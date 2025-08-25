package com.civicpark.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.RtoCookieDTO;
import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.entities.RtoOffice;
import com.civicpark.mapper.RtoOfficeMapper;
import com.civicpark.repository.RtoOfficeRepository;
import com.civicpark.utils.JwtTokenProvider;
import com.civicpark.utils.RtoUsernamePasswordAuthenticationToken;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RtoOfficeService {
	private final RtoOfficeRepository repository;
	private final RtoOfficeMapper mapper;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	// =====================================================================//
	/**
	 * Service to register new RTO Office.
	 * 
	 * @param dto
	 * @return
	 */
	public RtoResponseDTO registerNewOffice(@RequestBody RtoRequestDTO dto) {
		if (repository.findByEmail(dto.getEmail()) != null) {
			throw new ResourceConflictException("RTO office already exists");
		}

		RtoOffice rtoOffice = mapper.toEntity(dto);
		rtoOffice.setPassword(encoder.encode(rtoOffice.getPassword()));
		RtoOffice saved = repository.save(rtoOffice);

		return mapper.toDTO(saved);
	}

	// ========================================================================================//
	/**
	 * Authenticate RTO office and Generate Jwt token
	 * 
	 * @param dto
	 * @param response
	 * @return cookie
	 */
	public RtoResponseDTO loginRtoAndSetCookie(RtoCookieDTO dto, HttpServletResponse response) {
		Authentication authentication = authenticationManager
				.authenticate(new RtoUsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		// Holds authentication object
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails rtoDetails = (UserDetails) authentication.getPrincipal();


		// RTO office details
		RtoOffice rto = repository.findByEmail(dto.getEmail());
		RtoResponseDTO responseDTO = mapper.toDTO(rto);

		// Generate string
		String token = jwtTokenProvider.generateTokenForRTO(rtoDetails);

		// Set cookie
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60);
		cookie.setAttribute("SameSite", "None");
		response.addCookie(cookie);

		return responseDTO;
	}

	// ================================================================//
	/**
	 * Find Rto office by id
	 * 
	 * @param id
	 * @return dto
	 */
	public RtoResponseDTO getRtoDetailsById(Long id) {
		RtoOffice rtoOffice = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rto office doesn't exist"));

		return mapper.toDTO(rtoOffice);
	}

	// ============================================//
	/**
	 * List all RTO offices
	 * 
	 * @return list
	 */
	public List<RtoOffice> getListOfOffice() {
		List<RtoOffice> list = repository.findAll();
		return list;
	}

}
