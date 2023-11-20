
package com.jsbtech.gestao_vendas.modelo.conexao;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import java.sql.Connection;
import java.sql.SQLException;


public class ConexaoPostgres implements Conexao{

    @Override
    public Connection obterConexao() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void fecharConexao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
