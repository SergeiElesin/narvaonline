package com.elesinsergei.narvaonline.data;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PostParamHybridTestData implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return postDataProvider(); // Call method postDataProvider
    }

    // Data source for test cases
    public static Stream<Arguments> postDataProvider() {
        return Stream.of(
                // Case 1: A regular post should be visible to everyone
                arguments("Standard Post " + System.currentTimeMillis(), "publish", "", "This is open content", 201),
                // Case 2: Post with password – the text "Protected" should appear in the UI
                arguments("Secret Post " + System.currentTimeMillis(), "publish", "1234", "This is secret content", 201),
                // Case 3: Invalid data (no content or title)
                // Expecting that the API may return an error (depending on WP settings)
                arguments("    ", "publish", "", "", 400),
                //Case 4: HTML in title - XSS protection
                arguments("<script>alert(1)</script>", "publish", "", "content", 201),
                // Case 5: Very long title
                arguments("A".repeat(200), "publish", "", "content", 201),
                // Case 6: Special characters
                arguments("Test & Post <> \"quotes\"", "publish", "", "content", 201)
        );
    }
}
