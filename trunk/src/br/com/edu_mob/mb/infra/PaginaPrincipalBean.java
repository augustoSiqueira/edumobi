package br.com.edu_mob.mb.infra;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edu_mob.controller.AlunoController;
import br.com.edu_mob.controller.UsuarioController;
import br.com.edu_mob.entity.Aluno;
import br.com.edu_mob.entity.Usuario;
import br.com.edu_mob.exception.RNException;
import br.com.edu_mob.mb.DadosPessoaisBean;
import br.com.edu_mob.mb.GenericBean;
import br.com.edu_mob.message.ErrorMessage;
import br.com.edu_mob.util.MensagemUtil;

@ManagedBean
@ViewScoped
public class PaginaPrincipalBean extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DadosPessoaisBean.class.getName());

	private UsuarioController usuarioController;
	private AlunoController alunoController;

	private Aluno aluno;

	private final static long ID_ALUNO = 2L;

	@PostConstruct
	public void init() {

		this.usuarioController = (UsuarioController) this.getBean("usuarioController", UsuarioController.class);
		this.alunoController = (AlunoController) this.getBean("alunoController", AlunoController.class);

		try {

			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(usuario.getPerfil().getId().equals(ID_ALUNO)) {
				this.aluno = this.alunoController.pesquisarPorId(usuario.getId());
			}
		} catch (RNException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			this.addMessage(MensagemUtil.getMensagem(ErrorMessage.ERRO.getChave()), e.getListaMensagens());
		}

	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public static Logger getLogger() {
		return logger;
	}





}
