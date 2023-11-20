
package com.jsbtech.gestao_vendas.modelo.dao;

import com.jsbtech.gestao_vendas.modelo.dominio.Perfil;
import com.jsbtech.gestao_vendas.modelo.dominio.Usuario;
import com.jsbtech.gestao_vendas.modelo.exception.NegocioException;
import com.jsbtech.gestao_vendas.view.modelo.LoginDto;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

public class AutenticacaoDao {
    
    private final UsuarioDao usuarioDao;
    
    public AutenticacaoDao() {
        this.usuarioDao = new UsuarioDao();
    }
    
    public boolean temPermissao(Usuario usuario) {
        try {
            permissao(usuario);
            return true;
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Usuario sem permissao", 0);
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }
    
    private void permissao(Usuario usuario) {
        if(!Perfil.ADMIN.equals(usuario.getPerfil())){
            throw new NegocioException("Sem permissão para realizar essa ação");
        }
    }
    
    public Usuario login(LoginDto login) {
        Usuario usuario = usuarioDao.buscarUsuarioPeloUsuario(login.getUsuario());
        
        if(usuario == null || !usuario.isEstado()) return null;
            
        if(usuario.isEstado() && validaSenha(usuario.getSenha(), login.getSenha())){
            return usuario;
        }
        return null;
    }

    private boolean validaSenha(String senhaUsuario, String senhaLogin) {
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        return passwordEncoder.matches(senhaLogin, senhaUsuario);
    }
    
}// end AutenticacaoDao
