package com.ivan.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UrlShortenerController {

    @Autowired
    private ShortUrlRepository repository;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("url");

        if (longUrl == null || longUrl.isBlank()) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        String shortCode = UUID.randomUUID().toString().substring(0, 6);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl(longUrl);
        shortUrl.setShortCode(shortCode);

        repository.save(shortUrl);

        return ResponseEntity.ok(Map.of(
                "shortCode", shortCode,
                "shortUrl", "http://localhost:8080/" + shortCode
        ));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        Optional<ShortUrl> result = repository.findByShortCode(code);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        ShortUrl shortUrl = result.get();
        shortUrl.setClicks(shortUrl.getClicks() + 1);
        repository.save(shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", shortUrl.getLongUrl())
                .build();
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<?> getStats(@PathVariable String code) {
        Optional<ShortUrl> result = repository.findByShortCode(code);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        ShortUrl shortUrl = result.get();
        return ResponseEntity.ok(Map.of(
                "longUrl", shortUrl.getLongUrl(),
                "clicks", shortUrl.getClicks(),
                "createdAt", shortUrl.getCreatedAt()
        ));


        
    }


    
}