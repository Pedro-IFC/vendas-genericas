package BO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import DTO.ContaDTO;
import VIEW.ClienteView;

public class ClienteBO extends BO{
	
	public ClienteBO() {
		super();
	}

    public boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validarTelefone(String telefone) {
        String regex = "^[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefone);
        return matcher.matches();
    }

    public String calcularMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] hash = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String cadastrarCliente(ContaDTO cliente) {
    	List<ContaDTO> email = permanencia.getClientedao().findByEmail(cliente);
    	if(!(email.size()>0)) {
    		permanencia.getClientedao().create(cliente);
    		ClienteView.redirectTo="";
    		return "Cliente cadastrado com sucesso";
    	}else {
    		ClienteView.redirectTo="cadastro";
    		return "Este e-mail já foi cadastrado como cliente";
    	}
    }
    public String alterarDados(ContaDTO cliente) {
    	if(permanencia.getClientedao().update(cliente)){
        	return "Dados alterados com sucesso";
    	}else {
        	return "Dados não puderam ser alterados, entre em contato com o suporte";
    	}
    }
    public String logarCliente(ContaDTO cliente) {
    	List<ContaDTO> email = permanencia.getClientedao().findByEmail(cliente);
    	if((email.size()>0)) {
        	List<ContaDTO> pass = permanencia.getClientedao().findByEmailPassword(cliente);
        	if((pass.size()>0)) {
        		ClienteView.clienteLogado = pass.get(0);
        		ClienteView.loginStatus = true;
        		ClienteView.redirectTo="";
        		return "Cliente logado com sucesso";
        	}else {
        		ClienteView.redirectTo="login";
        		return "E-mail e senhas não coicidem";
        	}
    	}else {
    		ClienteView.redirectTo="login";
    		return "Este e-mail não foi cadastrado";
    	}
    }
}
