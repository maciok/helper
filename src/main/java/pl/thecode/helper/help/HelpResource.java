package pl.thecode.helper.help;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class HelpResource {

  private final HelpRepository helpRepository;

  @PostMapping(value = "/api/help", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity request(
    @AuthenticationPrincipal OAuth2AuthenticationToken userInfo,
    @RequestBody HelpRequest helpRequest) {

    var uuid = (String) userInfo.getPrincipal().getAttribute("sub");
    var help = new HelpEntity(
      uuid,
      helpRequest.getLocalization(),
      helpRequest.getHelpTimeBox(),
      helpRequest.getCategory(),
      helpRequest.getDescription()
    );

    helpRepository.save(help);
    return ResponseEntity.status(CREATED).build();
  }
}
