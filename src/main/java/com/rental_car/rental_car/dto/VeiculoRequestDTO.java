package com.rental_car.rental_car.dto;

import com.rental_car.rental_car.model.StatusVeiculo;
import com.rental_car.rental_car.model.Veiculo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VeiculoRequestDTO {

    @NotBlank(message = "A placa não pode ser vazia")
    @Pattern(
            regexp = "^[A-Za-z]{3}[0-9][A-Za-z0-9][0-9]{2}$",
            message = "A placa deve seguir o Modelo do Mercosul, como ABC1D23 ou o modelo antigo como ABC1234"
    )
    private String placa;

    @NotBlank(message = "O modelo não pode ser vazio")
    @Size(max = 50, message = "O modelo deve ter no máximo 50 caracteres")
    private String modelo;

    @NotNull(message = "o valor não pode ser vazio")
    @Positive(message = "o valor da diária deve ser maior que 0")
    private BigDecimal valorDiaria;

    @NotBlank(message = "A marca não pode ser vazia")
    @Size(max = 50, message = "A marca deve ter no máximo 50 caracteres")
    private String marca;

    public Veiculo toEntity(){
        Veiculo veiculo = new Veiculo();
        veiculo.setValorDiaria(this.valorDiaria);
        veiculo.setModelo(this.modelo);
        veiculo.setMarca(this.marca);
        veiculo.setPlaca(this.placa.toUpperCase());
        veiculo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
        return veiculo;
    }

}
