
package com.jsbtech.gestao_vendas.modelo.conexao;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoMysql implements Conexao{
    
    private Connection conectar;
    private final String USUARIO = "root";
    private final String SENHA = "root";
    private final String URL = "jdbc:mysql://localhost:3306/gestao_vendas?useSSL=false&serverTimezone=UTC";
    

    @Override
    public Connection obterConexao() throws SQLException {
        if (conectar == null) {
            conectar = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        
        return conectar;
    }
    
    @Override
    public void fecharConexao() throws SQLException {
        if(conectar != null)
            conectar.close();
    }
    
}
