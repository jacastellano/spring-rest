package com.jacastellano.quickpoll.controller;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jacastellano.quickpoll.domain.Vote;
import com.jacastellano.quickpoll.dto.error.ErrorDetail;
import com.jacastellano.quickpoll.repository.VoteRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@Api(value = "votes", description = "Vote API")
public class VoteController {

	@Inject
	private VoteRepository voteRepository;

	@RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Vote", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Vote Created Successfully", response = Void.class),
			@ApiResponse(code = 500, message = "Error creating Vote", response = ErrorDetail.class) })
	public ResponseEntity<Void> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
		vote = voteRepository.save(vote);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieves all the votes", response = Vote.class, responseContainer = "List")
	public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
		return voteRepository.findByPoll(pollId);
	}

}
