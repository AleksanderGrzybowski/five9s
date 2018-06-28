package pl.kelog.five9s.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceHealthController {
    
    private final ApiService apiService;
    
    @GetMapping("/list")
    public ResponseEntity<List<ApiService.ServiceDto>> list() {
        return new ResponseEntity<>(apiService.getAll(), HttpStatus.OK);
    }
}
