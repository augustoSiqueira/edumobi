package br.com.edu_mob.comparator;

import java.util.Comparator;

import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;

public class ComparatorResultadoSimulado implements Comparator<ResultadoSimuladoDTO> {

	@Override
	public int compare(ResultadoSimuladoDTO ResultadoSimuladoDTO, ResultadoSimuladoDTO ResultadoSimuladoDTO2) {
		if(ResultadoSimuladoDTO.getQtdAcertos() < ResultadoSimuladoDTO2.getQtdAcertos()) {
			return 1;
		} else if(ResultadoSimuladoDTO.getQtdAcertos() > ResultadoSimuladoDTO2.getQtdAcertos()) {
			return -1;
		} else {
			return 0;
		}
	}

}
