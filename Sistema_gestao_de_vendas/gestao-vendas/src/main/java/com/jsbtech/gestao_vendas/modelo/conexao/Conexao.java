
package com.jsbtech.gestao_vendas.modelo.conexao;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import java.sql.Connection;
import java.sql.SQLException;


public interface Conexao {
    
    public Connection obterConexao() throws SQLException;
    public void fecharConexao()throws SQLException;
    
}
