package com.jacastellano.quickpoll.v1.controller;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jacastellano.quickpoll.domain.Poll;
import com.jacastellano.quickpoll.dto.error.ErrorDetail;
import com.jacastellano.quickpoll.exception.ResourceNotFoundException;
import com.jacastellano.quickpoll.repository.PollRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController("pollControllerV1")
@RequestMapping("/v1/")
@Api(value = "polls", description = "Poll API")
public class PollController {

	@Inject
	private PollRepository pollRepository;

	@RequestMapping(value = "/polls", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieves all the polls", response = Poll.class, responseContainer = "List")
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(allPolls, HttpStatus.OK);
	}

	@RequestMapping(value = "/polls", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Poll", notes = "The newly created poll Id will be sent in the location response header", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Poll Created Successfully", response = Void.class),
			@ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class) })
	public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {

		poll = pollRepository.save(poll);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(poll.getId())
				.toUri();
		responseHeaders.setLocation(newPollUri);

		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieves a Poll associated with the pollId", response = Poll.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Poll.class),
			@ApiResponse(code = 404, message = "Poll not found", response = ErrorDetail.class) })
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
		verifyPoll(pollId);
		Optional<Poll> poll = pollRepository.findById(pollId);
		return new ResponseEntity<>(poll, HttpStatus.OK);
	}

	@RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
	@ApiOperation(value = "Updates a Poll associated with the pollId", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Poll.class),
			@ApiResponse(code = 404, message = "Poll not found", response = ErrorDetail.class) })
	public ResponseEntity<Void> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
		verifyPoll(pollId);
		pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes a Poll associated with the pollId", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Poll.class),
			@ApiResponse(code = 404, message = "Poll not found", response = ErrorDetail.class) })
	public ResponseEntity<Void> deletePoll(@PathVariable Long pollId) {
		verifyPoll(pollId);
		pollRepository.deleteById(pollId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
		Optional<Poll> poll = pollRepository.findById(pollId);
		if (poll == null || !poll.isPresent()) {
			throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
		}
	}
}