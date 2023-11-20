
package com.jsbtech.gestao_vendas.modelo.dao;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import com.jsbtech.gestao_vendas.modelo.conexao.Conexao;
import com.jsbtech.gestao_vendas.modelo.conexao.ConexaoMysql;
import com.jsbtech.gestao_vendas.modelo.dominio.Perfil;
import com.jsbtech.gestao_vendas.modelo.dominio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UsuarioDao {
    
    private final Conexao conexao;
    
    public UsuarioDao(){
        this.conexao = new ConexaoMysql();
    }
    
    public String salvar(Usuario usuario) {
        return usuario.getId() == 0L ? adicionar(usuario) : editar(usuario);
    }

    private String adicionar(Usuario usuario) {
        String sql = "INSERT INTO usuario(nome, usuario, senha, perfil, estado) VALUES(?, ?, ?, ?, ?)";
        
        Usuario usuarioTemp = buscarUsuarioPeloUsuario(usuario.getUsuario());
        
        if(usuarioTemp != null) {
            return String.format("Error : username %s já existe no banco de dados", usuario.getUsuario());
        }
        
        try {
            
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, usuario);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario adicionado com sucesso" : "Não foi possivel adicionar usuario";
            
        } catch (SQLException e) {
            
            return String.format("Error: %s", e.getMessage());
            
        }
    }

    private String editar(Usuario usuario) {
        
        String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ?, perfil = ?, estado = ? WHERE id = ?";
        
        try {
            
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, usuario);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario editadodo com sucesso" : "Não foi possivel editar usuario";
            
        } catch (SQLException e) {
            
            return String.format("Error: %s", e.getMessage());
            
        }
    }

    private void preencherValoresPreparedStatement(PreparedStatement preparedStatement, Usuario usuario) throws SQLException {
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        String senhaCrypto = passwordEncoder.encode(usuario.getSenha());
        
        preparedStatement.setString(1, usuario.getNome());
        preparedStatement.setString(2, usuario.getUsuario());
        preparedStatement.setString(3, senhaCrypto);
        preparedStatement.setString(4, usuario.getPerfil().name());
        preparedStatement.setBoolean(5, usuario.isEstado());
        
        if(usuario.getId() != 0L) {
            preparedStatement.setLong(6, usuario.getId());
        }
    }
    
    public List<Usuario> buscarTodosUsuarios() {
        String sql = "SELECT * FROM usuario";
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while(result.next()) {
                usuarios.add(getUsuario(result));
            }
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return usuarios;
    }
    
    private Usuario getUsuario(ResultSet result) throws SQLException {
        Usuario usuario = new Usuario();
        
        usuario.setId(result.getLong("id"));
        usuario.setNome(result.getString("nome"));
        usuario.setUsuario(result.getString("usuario"));
        usuario.setSenha(result.getString("senha"));
        usuario.setPerfil(Perfil.valueOf(result.getString("perfil")));
        usuario.setEstado(result.getBoolean("estado"));
        usuario.setDataHoraCriacao(result.getObject("data_hora_criacao", LocalDateTime.class ));
        usuario.setUltimoLogin(result.getObject("ultimo_login", LocalDateTime.class ));
        
        return usuario;
    }
    
    public Usuario buscarUsuarioPeloId(Long id) {
        String sql = String.format("SELECT * FROM usuario WHERE id = %d", id);
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()) {
                return getUsuario(result);
            }
            
        } catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public Usuario buscarUsuarioPeloUsuario(String usuario) {
        String sql = String.format("SELECT * FROM usuario WHERE usuario = '%s'", usuario);//TODO trocar id por usuario
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()) {
                return getUsuario(result);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public String deleteUsuarioPeloId(Long id) {
        String sql = String.format("DELETE FROM usuario WHERE id = %d", id);
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario apagado com sucesso" : "Nao foi possivel apagar";
            
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
    
    public void actualizarUltimoLogin(Usuario usuario) {
        String sql = "UPDATE usuario SET ultimo_login = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preparedStatement.setString(1, LocalDateTime.now().toString());
            preparedStatement.setLong(2, usuario.getId());
            
            int resultado = preparedStatement.executeUpdate();
            
            System.out.println(String.format("Actualizacao do ultimo login: %s", 
                    resultado == 1 ? "Com sucesso!!!" : "Sem Sucesso."));            
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
}//end UsuarioDao
