package com.civicpark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.service.RtoOfficeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rto-office")
@AllArgsConstructor
/**
 * Controller to handle all RTO office related API end points.
 */
public class RtoOfficeController {
	private final RtoOfficeService service;

	// ==================================================================//
	/**
	 * Handles RTO details
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get-details/{id}")
	public ResponseEntity<?> getRtoOfficeDetails(@PathVariable Long id) {
		try {
			RtoResponseDTO responseDTO = service.getRtoDetailsById(id);
			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RTO details with id: " + id + "not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong");
		}
	}
}
