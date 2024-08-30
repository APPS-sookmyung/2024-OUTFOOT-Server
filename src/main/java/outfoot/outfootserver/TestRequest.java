package outfoot.outfootserver;

import org.springframework.web.multipart.MultipartFile;

public record TestRequest(MultipartFile file) {
}
