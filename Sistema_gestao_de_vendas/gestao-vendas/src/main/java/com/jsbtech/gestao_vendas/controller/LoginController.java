
package com.jsbtech.gestao_vendas.controller;

import com.jsbtech.gestao_vendas.modelo.dao.AutenticacaoDao;
import com.jsbtech.gestao_vendas.modelo.dominio.Usuario;
import com.jsbtech.gestao_vendas.view.formulario.Dashboard;
import com.jsbtech.gestao_vendas.view.formulario.Login;
import com.jsbtech.gestao_vendas.view.modelo.LoginDto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */
public class LoginController implements ActionListener{
    
    private final Login login;
    private AutenticacaoDao autenticacaoDao;

    public LoginController(Login login) {
        this.login = login;
        this.autenticacaoDao = new AutenticacaoDao();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        String acao = ae.getActionCommand().toLowerCase();
        
        switch (acao) {
            case "login": login(); break;
            case "cancelar": cancelar(); break;    
            default: throw new AssertionError();
        }
    }

    private void login() {
        String usuario = this.login.getTxtLoginUsuario().getText();
        String senha = this.login.getTxtLoginSenha().getText();
        
        if(usuario.equals("") || senha.equals("")) {
            this.login.getLabelLoginMensagem().setText("Usuário e senha devem ser preenchidos.");
            return;
        }
        
        LoginDto loginDto = new LoginDto(usuario, senha);
        Usuario usuarioTemp = this.autenticacaoDao.login(loginDto);
        
        if (usuarioTemp != null) {
            System.out.println("Sucesso: " + usuarioTemp.getUsuario());
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            dashboard.getLabelBenvindoUsuario().setText(String.format("Bem-vindo %s", usuarioTemp.getNome()));
            dashboard.getLabelUsuarioLogadoId().setText(Long.toString(usuarioTemp.getId()));
            this.login.setVisible(false);
            limparCampos();
        }else {
            this.login.getLabelLoginMensagem().setText("Usuário e senha incorreto");
        }
    }

    private void cancelar() {
        int confirmar = JOptionPane.showConfirmDialog(login, "Tem certeza que deseja sair?", "Sair do sistema", JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void limparCampos() {
        this.login.getTxtLoginUsuario().setText("");
        this.login.getTxtLoginSenha().setText("");
        this.login.getLabelLoginMensagem().setText("");
    }
    
}
