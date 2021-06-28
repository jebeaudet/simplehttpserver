# simplehttpserver
Having fun implementing an HTTP/1.1 server without any dependency! Little project implemented in rainy afternoon, so many things are missing but it's working!

# How to build and use
Java 11 minimum is required. `mvn package` will build an executable jar in the `target/` directory. This will start the server on port 8765 and echo your request body in a 200.

# What's missing
- Proper UTF-8 support in the request line
- Buffer in stream handling
- Percent encoding support
- Chunked requests ans responses
- Better error handling on malformed request
- Tests
- A billion of other things from RFC 2616 :)
