package com.sofka.movimientos.services;


import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;

public interface MovementsService {

    ResponseData createMovement(MovementsDTO.createMovement movementsDTO);

    ResponseData listMovement();

    ResponseData reportMovement(MovementsDTO.reportMovement reportMovement);

    ResponseData reportMovementFormat(MovementsDTO.reportMovement reportMovement);


}
