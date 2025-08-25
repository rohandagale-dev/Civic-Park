package com.civicpark.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.civicpark.entities.RtoOffice;
import com.civicpark.repository.RtoOfficeRepository;
import com.civicpark.utils.RtoUserDetails;

@Service
public class RtoUserDetailsService implements UserDetailsService {

	private final RtoOfficeRepository rtoRepository;

	public RtoUserDetailsService(RtoOfficeRepository rtoRepository) {
		this.rtoRepository = rtoRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		RtoOffice rto = rtoRepository.findByEmail(email);

		return new RtoUserDetails(rto);
	}
}
