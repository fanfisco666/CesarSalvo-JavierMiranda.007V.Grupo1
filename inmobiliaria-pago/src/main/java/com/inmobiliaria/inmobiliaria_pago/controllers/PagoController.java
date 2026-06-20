package com.inmobiliaria.inmobiliaria_pago.controllers;

import com.inmobiliaria.inmobiliaria_pago.dtos.request.PagoRequest;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.PagoResponse;
import com.inmobiliaria.inmobiliaria_pago.services.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pagos", description = "Operaciones relacionadas con pagos en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Listar pagos", description = "Obtiene una lista de todos los pagos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente")
    @GetMapping
    public List<PagoResponse> listar() {
        return pagoService.obtenerTodos();
    }

    @Operation(summary = "Obtener pago por ID", description = "Obtiene los detalles de un pago específico por su ID")
    @ApiResponse(responseCode = "200", description = "Pago obtenido exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener pagos por contrato", description = "Obtiene una lista de pagos asociados a un contrato específico")
    @ApiResponse(responseCode = "200", description = "Pagos obtenidos exitosamente")
    @GetMapping("/contrato/{idContrato}")
    public List<PagoResponse> obtenerPorContrato(@PathVariable Long idContrato) {
        return pagoService.obtenerPorContrato(idContrato);
    }

    @Operation(summary = "Obtener pagos por cliente", description = "Obtiene una lista de pagos asociados a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Pagos obtenidos exitosamente")
    @GetMapping("/cliente/{idCliente}")
    public List<PagoResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return pagoService.obtenerPorCliente(idCliente);
    }

    @Operation(summary = "Obtener pagos en mora", description = "Obtiene una lista de pagos que están en mora")
    @ApiResponse(responseCode = "200", description = "Pagos en mora obtenidos exitosamente")
    @GetMapping("/mora")
    public List<PagoResponse> obtenerEnMora() {
        return pagoService.obtenerEnMora();
    }

    @Operation(summary = "Crear pago", description = "Crea un nuevo pago en el sistema")
    @ApiResponse(responseCode = "201", description = "Pago creado exitosamente")
    @PostMapping
    public ResponseEntity<PagoResponse> guardar(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(pagoService.guardar(request));
    }

    @Operation(summary = "Confirmar pago", description = "Confirma un pago específico por su ID")
    @ApiResponse(responseCode = "200", description = "Pago confirmado exitosamente")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PagoResponse> confirmarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.confirmarPago(id));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un pago específico por su ID")
    @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}