package com.gtunes



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Song)
class SongTests {

    void testMinimumDuration() {
	// set the Song class up for constraints testing . . .
	mockForConstraintsTests Song
	// create a new Song
	def song = new Song(title: 'Some Title',
						artist: 'Some Artist',
						duration: 0)
	// make sure that validation fails . . .
	assert !song.validate()
	// make sure that the 'min' constraint failed . . .
	assert 'min' == song.errors['duration']
	}
}
