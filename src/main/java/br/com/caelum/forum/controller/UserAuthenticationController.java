package br.com.caelum.forum.controller;

import br.com.caelum.forum.security.controller.dto.input.LoginInputDto;
import br.com.caelum.forum.security.controller.dto.output.AuthenticationTokenOutputDto;
import br.com.caelum.forum.security.jwt.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationTokenOutputDto> authenticate(@RequestBody LoginInputDto loginInputDto) {
		UsernamePasswordAuthenticationToken token = loginInputDto.build();
		try {
			Authentication auth = authenticationManager.authenticate(token);
			String jwt = tokenManager.generateToken(auth);
			AuthenticationTokenOutputDto dtoOutput = new AuthenticationTokenOutputDto("Bearer", jwt);
			return ResponseEntity.ok(dtoOutput);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
