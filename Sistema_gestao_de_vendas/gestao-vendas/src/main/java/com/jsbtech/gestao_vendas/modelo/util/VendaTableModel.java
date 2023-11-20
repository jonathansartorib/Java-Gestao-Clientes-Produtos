
package com.jsbtech.gestao_vendas.modelo.util;

/**
 *
 * @author Jonathan Sartori Bruzarrosco <jonathansartorib@gmail.com>
 */

import com.jsbtech.gestao_vendas.modelo.dominio.Venda;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class VendaTableModel extends AbstractTableModel {
    
    private List<Venda> vendas;
    private String [] colunas = {"ID", "CLIENTE", "TOTAL", "VALOR PAGO", "DESCONTO", "TROCO", "DATA", "VENDIDO POR"};

    public VendaTableModel(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public int getRowCount() {
        return vendas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Venda venda = vendas.get(linha);
        
        switch(coluna) {
            case 0: return venda.getId();
            case 1: return venda.getCliente().getNome();
            case 2: return venda.getTotalDaVenda();
            case 3: return venda.getValorPago();
            case 4: return venda.getDesconto();
            case 5: return venda.getTroco();
            case 6: return venda.getDataHoraCriacao();
            case 7: return venda.getUsuario().getNome();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; 
    }
    
}


