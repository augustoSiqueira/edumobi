package br.com.edu_mob.report;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import br.com.edu_mob.entity.infra.ResultadoSimuladoDTO;

public class RelatorioDesempenhoSimulado extends Relatorio {

	public RelatorioDesempenhoSimulado(List<ResultadoSimuladoDTO> listaAlunos, @SuppressWarnings("rawtypes") Map parametros, InputStream caminho) {
		super();
		this.caminho = caminho;
		this.lista = listaAlunos;
		this.parametros = parametros;
	}

}
