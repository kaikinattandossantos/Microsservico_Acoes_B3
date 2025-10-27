package main.java.com.base.demo.Telegram.Controller;

import com.base.telegram.dto.TelegramRequestDTO;
import com.base.telegram.service.TelegramService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telegram")
public class TelegramController {

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody TelegramRequestDTO request) {
        telegramService.sendNotification(request.getMessage());
    }
}
