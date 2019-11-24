package pl.thecode.helper.help;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.thecode.helper.chat.ChatDto;
import pl.thecode.helper.chat.ChatFacade;
import pl.thecode.helper.notification.NotificationService;
import pl.thecode.helper.user.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
class HelpResource {

  private final HelpRepository helpRepository;
  private final NotificationService notificationService;
  private final UserFacade userFacade;
  private final ChatFacade chatFacade;
  
  @PostMapping(value = "/api/help", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity request(
    @AuthenticationPrincipal OAuth2AuthenticationToken userInfo,
    @RequestBody HelpRequest helpRequest) {

    var uuid = (String) userInfo.getPrincipal().getAttribute("sub");
    var userDisabilities = userFacade.getUserDisabilities(uuid);
    var help = new HelpEntity(
      uuid,
      userDisabilities,
      helpRequest.getLocalization(),
      helpRequest.getHelpTimeBox(),
      helpRequest.getCategory(),
      helpRequest.getDescription()
    );

    HelpEntity savedHelp = helpRepository.save(help);
    List<String> nearbyUsers = userFacade.getNearbyUsersUuidExcept(uuid);
    notificationService.notifyUsers(nearbyUsers, savedHelp.getId());
    return ResponseEntity.status(CREATED).build();
  }

  @GetMapping(value = "/api/help", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<List<HelpDto>> fetch() {

    var allRequests = helpRepository.findAll()
                                    .stream()
                                    .map(HelpEntity::createDto)
                                    .collect(Collectors.toList());
    return ResponseEntity.ok(allRequests);
  }
  
  @GetMapping(value = "/api/help/{helpId}", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<HelpDto> fetchSingle(@PathVariable("helpId") long helpId) {

    return helpRepository.findById(helpId)
                         .map(HelpEntity::createDto)
                         .map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/api/help/{helpId}/assigned")
  ResponseEntity<ChatDto> assign(@PathVariable("helpId") long helpId, @AuthenticationPrincipal OAuth2AuthenticationToken userInfo) {
    var helpRequest = helpRepository.findById(helpId)
                                    .orElseThrow(() -> new IllegalArgumentException(format("Cannot find help regest '%d'", helpId)));

    var uuid = (String) userInfo.getPrincipal().getAttribute("sub");
    if (isNull(helpRequest.getHelperId())) {
      helpRequest.assignHelper(uuid);
      helpRepository.save(helpRequest);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    var newChat = chatFacade.createNewChat(helpRequest.getNeedyId(), helpRequest.getHelperId());
    return ResponseEntity.ok(newChat);
    
  }
  
  @DeleteMapping(value = "/api/help/{helpId}", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity close(@PathVariable("helpId") long helpId) {
    var help = helpRepository.findById(helpId)
                             .orElseThrow(() -> new IllegalStateException(format("Cannot find request for '%d'", helpId)));
    help.close();
    helpRepository.save(help);
    return ResponseEntity.ok().build();
  }
}
