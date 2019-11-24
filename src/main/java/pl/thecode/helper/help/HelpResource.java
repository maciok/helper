package pl.thecode.helper.help;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thecode.helper.user.UserFacade;

@RestController
@AllArgsConstructor
class HelpResource {

  private final HelpRepository helpRepository;
  private final UserFacade userFacade;

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

    helpRepository.save(help);
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
  
  @DeleteMapping(value = "/api/help/{helpId}", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity close(@PathVariable("helpId") long helpId) {
    var help = helpRepository.findById(helpId)
                             .orElseThrow(() -> new IllegalStateException(format("Cannot find request for '%d'", helpId)));
    help.close();
    helpRepository.save(help);
    return ResponseEntity.ok().build();
  }
}
