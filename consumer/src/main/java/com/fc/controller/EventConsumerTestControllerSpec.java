package com.fc.controller;

import com.fc.event.CommentEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;


@Tag(name = "Event Consumer 호출 테스트 API")
public interface EventConsumerTestControllerSpec {

    @Operation(

            requestBody = @RequestBody(
                content = {
                        @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                examples = {
                                        @ExampleObject(name = COMMENT_EVENT_PAYLOAD)
                                }
                        )
                }
            )
    )
    void comment(CommentEvent event);

    String COMMENT_EVENT_PAYLOAD = """
            {
               "type": "ADD",
               "postId": 1,
               "userId": 2,
               "commentId": 3
             }
            """;
}