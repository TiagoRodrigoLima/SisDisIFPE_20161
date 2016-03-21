package ifpe20161.echoserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
	
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8181);
            Socket incoming = s.accept();
            try {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();
                
                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
                
                PrintMenuPrincipal(out);
                System.out.println("Conexão estabelecida com o cliente.");
                // echo client input
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    int opcao = Integer.parseInt(in.nextLine());
					switch(opcao)
					{
						case 0:
							System.out.println("Conexão encerrada com o cliente!");
							out.println("Até a próxima! Conexão encerrada.");
							done = true;
							break;
						case 1:
							System.out.println("Conversor de medidas selecionado!");
							ConversorMedidas(out, in);
							break;
						case 2:
							System.out.println("Conversor monetário selecionado!");
							ConversorMonetario(out, in);
							break;
						case 3:
							System.out.println("Conversor de temperatura selecionado!");
							ConversorTemperatura(out, in);
							break;
						default:
							System.out.println("Cliente não digitou valor válido em menu principal: " + opcao);
							out.println("Valor inserido não reconhecido como válido.\n" +
									"Por favor, insira um valor inteiro dentre os listados no menu.");
					}
					if(!done){
						PrintMenuPrincipal(out);
					}
                }
            } finally {
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param out
     */
    public static void PrintMenuPrincipal(PrintWriter out)
    {
    	out.println("\nBem-vindo! Este é um programa de conversão monetária, medidas e temperatura.");
    	out.println("Digite o valor correspondente ao conversor desejado:");
    	out.println("1 - Medidas");
    	out.println("2 - Monetário");
    	out.println("3 - Temperatura");
    	out.println("0 - Sair");
    }
    
    /**
     * 
     * @param out
     * @param in
     */
    public static void ConversorMedidas(PrintWriter out, Scanner in)
    {
    	double medidaCent = 0;
    	boolean done = false;
    	
    	out.println("\nVocê selecionou o conversor de medidas - opção 1.");
    	out.println("Digite a medida em centímetros(cm):");
    	while(!done && in.hasNextLine())
    	{
    		String medidaCentStr = in.nextLine();
    		
    			if(medidaCentStr.matches("[0-9.]*"))
    			{
    				medidaCent = Double.parseDouble(medidaCentStr);
    				out.println("Medida recebida: " + round(medidaCent,2) + " centímetros.");
    				done = true;
    			} else {
    				out.println("Valor digitado não é numérico! Digite novamente o valor em centímetros:");
    			}
    	}
    	double medidaPol = medidaCent * 0.394;
    	
    	out.println(round(medidaCent,2) + " centímetros(cm) equivale a " + round(medidaPol,2) + " polegadas(pol)");
    }
    
    /**
     * 
     * @param out
     * @param in
     */
    public static void ConversorMonetario(PrintWriter out, Scanner in)
    {
    	double valorReais = 0;
    	boolean done = false;
    	
    	out.println("\nVocê selecionou o conversor monetário - opção 2.");
    	out.println("Digite o valor em reais(R$):");
    	while(!done && in.hasNextLine())
    	{
    		String valorReaisStr = in.nextLine();
    		
    			if(valorReaisStr.matches("[0-9.]*"))
    			{
    				valorReais = Double.parseDouble(valorReaisStr);
    				out.println("Valor em reais recebido(R$): " + round(valorReais,2));
    				done = true;
    			} else {
    				out.println("Valor digitado não é numérico! Digite novamente o valor em reais:");
    			}
    	}
    	double valorDolares = valorReais / 3.5802;
    	
    	out.println("R$" + round(valorReais,2) + " (reais) equivale a $" + round(valorDolares,2) + " (dólares)");
    }
    
    /**
     * 
     * @param out
     * @param in
     */
    public static void ConversorTemperatura(PrintWriter out, Scanner in)
    {
    	double valorTemperaturaCelsius = 0;
    	boolean done = false;
    	
    	out.println("\nVocê selecionou o conversor de temperatura - opção 3.");
    	out.println("Digite a temperatura em Celsius(°C):");
    	while(!done && in.hasNextLine())
    	{
    		String temperaturaCelsiusStr = in.nextLine();
    		
    			if(temperaturaCelsiusStr.matches("[0-9.]*"))
    			{
    				valorTemperaturaCelsius = Double.parseDouble(temperaturaCelsiusStr);
    				out.println("Temperatura recebida: " + round(valorTemperaturaCelsius,3));
    				done = true;
    			} else {
    				out.println("Valor digitado não é numérico! Digite novamente o valor em reais:");
    			}
    	}
    	double valorTemperaturaKelvin = valorTemperaturaCelsius + 273;
    	double valorTemperaturaFahrenheit = valorTemperaturaCelsius * 9 / 5 + 32; 
    	
    	out.println(round(valorTemperaturaCelsius,3) + "°C (Celsius) equivale a "
    		+ round(valorTemperaturaFahrenheit,3) + "F (Fahrenheit)"
    		+ " e " + round(valorTemperaturaKelvin,3) + "K (Kelvin)");
    }
    
    /**
     * 
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
