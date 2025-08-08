package com.civicpark.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.civicpark.ParkingTicketSystemApplication;
import com.civicpark.custom_exceptions.BadCredentialsException;
import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.RtoLoginDTO;
import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.entities.RtoOffice;
import com.civicpark.mapper.RtoOfficeMapper;
import com.civicpark.repository.RtoOfficeRepository;
import com.civicpark.utils.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RtoOfficeService {

	private final ParkingTicketSystemApplication parkingTicketSystemApplication;
	private final RtoOfficeRepository repository;
	private final RtoOfficeMapper mapper;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtTokenProvider;


	// ================== RTO Registration ==================//
	public RtoResponseDTO registerNewOffice(@RequestBody RtoRequestDTO dto) {
		if (repository.findByEmail(dto.getEmail()) != null) {
			throw new ResourceConflictException("RTO office already exists");
		}

		RtoOffice rtoOffice = mapper.toEntity(dto);
		rtoOffice.setPassword(encoder.encode(rtoOffice.getPassword()));
		RtoOffice saved = repository.save(rtoOffice);

		return mapper.toDTO(saved);
	}

	// ================== RTO Login ==================//
	public String loginRtoOffice(RtoLoginDTO dto) {
		RtoOffice rtoOffice = repository.findByEmail(dto.getEmail());

		if (rtoOffice == null) {
			throw new ResourceNotFoundException("RTO office does not exist");
		}
		
		if(!encoder.matches(dto.getPassword(), rtoOffice.getPassword())) {
			System.out.println("Wrong password");
			throw new BadCredentialsException("Incorrect password");
		}

		RtoResponseDTO response = mapper.toDTO(rtoOffice);
		
		return jwtTokenProvider.generateRtoToken(response);
	}

	// ================== List Officeses ==================//
	public List<RtoOffice> getListOfOffice() {
		List<RtoOffice> list = repository.findAll();
		return list;
	}
}
