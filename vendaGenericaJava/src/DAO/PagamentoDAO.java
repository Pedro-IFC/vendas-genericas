package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DBConnection.SQLConnection;
import DTO.ContaDTO;
import DTO.PagamentoDTO;
import ENUMS.FormaPagamento;
import ENUMS.StatusPagamento;

public class PagamentoDAO extends DAO {

	protected String primaryKey = "idPagamento";
	public String table = "pagamento";
    
    public List<String> fillable = new ArrayList<String>(Arrays.asList(
    		"formaPagamento",
    		"status",
    		"dataAlteracao",
    		"idCarrinho"
	));
    
    public String getTable() {
		return table;
	}

	public List<String> getFillable() {
		return fillable;
	}

	public boolean create(PagamentoDTO pagamento) {
    	try {
    		Connection conn = SQLConnection.connect();
            StringBuilder strBuilder = new StringBuilder();
            
            strBuilder.append("INSERT INTO ");
            strBuilder.append(getTable());
            strBuilder.append(fillable.toString().replace('[', '(').replace(']', ')'));
            strBuilder.append(" VALUES (?, ?, ?, ?)");
            
            PreparedStatement preparedStmt = conn.prepareStatement(strBuilder.toString());
            preparedStmt.setString(1, pagamento.getFormaPagamento().name());
            preparedStmt.setString(2, pagamento.getStatus().name());
            preparedStmt.setString(3, pagamento.getData().toString());
            preparedStmt.setInt(4, pagamento.getCarrinho());
            
            preparedStmt.executeUpdate();
            preparedStmt.close();
            conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	public boolean update(PagamentoDTO pagamento) {
		try {
			Connection conn = SQLConnection.connect();
			StringBuilder strBuilder = new StringBuilder();
			
            strBuilder.append("UPDATE ");
            strBuilder.append(getTable());
            strBuilder.append(" SET formaPagamento = ?, status = ?, dataAlteracao = ? WHERE  ");
            strBuilder.append(this.primaryKey);
            strBuilder.append(" = ");
            strBuilder.append(pagamento.getId());
            
            PreparedStatement preparedStmt = conn.prepareStatement(strBuilder.toString());
           
            preparedStmt.setString(1, pagamento.getFormaPagamento().name());
            preparedStmt.setString(2, pagamento.getStatus().name());
            preparedStmt.setString(3, pagamento.getData().toString());
            
            int affectedRows = preparedStmt.executeUpdate();
            
            preparedStmt.close();
            conn.close();
            
            return affectedRows > 0;
		} catch (Exception e) {
	       	 e.printStackTrace();
	         return false;
	    }
	}
	public boolean delete(PagamentoDTO pagamento) {
		try {
			Connection conn = SQLConnection.connect();
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("DELETE FROM ");
			strBuilder.append(getTable());
			
            strBuilder.append(" WHERE ");
            strBuilder.append(this.primaryKey);
            strBuilder.append("= ");
            strBuilder.append(pagamento.getId());
            PreparedStatement preparedStmt = conn.prepareStatement(strBuilder.toString());
            
            
            preparedStmt.executeUpdate();
            preparedStmt.close();
            conn.close();
            return true;
		} catch (Exception e) {
	       	 e.printStackTrace();
	         return false;
	    }
	}

	public List<PagamentoDTO> get() {
        try {
            Connection conn = SQLConnection.connect();
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("SELECT * FROM ");
            strBuilder.append(getTable());
            PreparedStatement preparedStmt = conn.prepareStatement(strBuilder.toString());
            ResultSet rs = preparedStmt.executeQuery();
            List<PagamentoDTO> listObj = mountList(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public List<PagamentoDTO> find(PagamentoDTO pagamento) {
        try {
            Connection conn = SQLConnection.connect();
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("SELECT * FROM ");
            strBuilder.append(getTable());
            strBuilder.append("	WHERE ");
            strBuilder.append(this.primaryKey);
            strBuilder.append("= ?");
            
            PreparedStatement preparedStmt = conn.prepareStatement(strBuilder.toString());
            
            preparedStmt.setInt(1, pagamento.getId());
            ResultSet rs = preparedStmt.executeQuery();
            List<PagamentoDTO> listObj = mountList(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public List<PagamentoDTO> mountList(ResultSet rs) {
		List<PagamentoDTO> listObj = new ArrayList<PagamentoDTO>();
        try {
            while (rs.next()) {
            	PagamentoDTO obj = new PagamentoDTO();
                obj.setId(rs.getInt(1));
                
                FormaPagamento formaF = FormaPagamento.valueOf(rs.getString(2).toUpperCase());
                obj.setFormaPagamento(formaF);

                StatusPagamento statusF = StatusPagamento.valueOf(rs.getString(3).toUpperCase());
                obj.setStatus(statusF);
                
                obj.setCarrinho(rs.getInt(4));
                
                String dateString = rs.getString(5);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            	LocalDate localDate = LocalDate.parse(dateString, formatter);
                obj.setData(localDate);
                
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
}
