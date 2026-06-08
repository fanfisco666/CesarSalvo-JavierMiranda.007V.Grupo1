package com.inmobiliaria.inmobiliaria_visita.client;

import com.inmobiliaria.inmobiliaria_visita.dtos.request.NotificacionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inmobiliaria-notificacion", url = "http://inmobiliaria-notificacion:8087")
public interface NotificacionClient {

    @PostMapping("/api/v1/notificaciones")
    void enviarNotificacion(@RequestBody NotificacionRequest request);
}