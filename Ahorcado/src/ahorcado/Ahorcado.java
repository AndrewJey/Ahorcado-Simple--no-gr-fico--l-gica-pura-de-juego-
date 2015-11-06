/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ahorcado;

import javax.swing.JOptionPane;

/**
 *
 * @author UTN
 */
public class Ahorcado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char digito='.';
        String palabraOculta;
        int cantidadLetras, aleatorio;
        char descompuesta[];
        char impresion[];
        int cont = 0;
        int contOportunidades = 0;
        int contDeComparacionDigitada;
        boolean falla;
        String algoPorCulpaDelProfe;
        aleatorio = (int) (Math.random() * 5) + 1;
        palabraOculta = "";
        switch (aleatorio) {
            case 1:
                palabraOculta = "alado";
                break;
            case 2:
                palabraOculta = "automotor";
                break;
            case 3:
                palabraOculta = "herradura";
                break;
            case 4:
                palabraOculta = "extravaganza";
                break;
            case 5:
                palabraOculta = "caballeria";
        }
        cantidadLetras = palabraOculta.length();
        descompuesta = palabraOculta.toCharArray();
        impresion = new char[descompuesta.length];
        while (cont <= descompuesta.length - 1) {
            impresion[cont] = '?';
            cont++;
        }
         while (contOportunidades < 8){
            contDeComparacionDigitada = 0;
            falla=false;
            algoPorCulpaDelProfe = JOptionPane.showInputDialog("Digite 1 digito que desea intentar");
            if(algoPorCulpaDelProfe.length()==1){
                digito=algoPorCulpaDelProfe.charAt(0);
            }else{
                if(algoPorCulpaDelProfe.equalsIgnoreCase(palabraOculta)){
                    JOptionPane.showMessageDialog(null, "GANASTE!!!");
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(null, "PERDISTE!");
                    System.exit(0);
                }
            }
            while (contDeComparacionDigitada < descompuesta.length) {
                if (descompuesta[contDeComparacionDigitada]==digito) {
                    impresion[contDeComparacionDigitada] = digito;
                    falla = true;
                }
                contDeComparacionDigitada++;
            }
            if (!falla) {
                contOportunidades++;
            }
            JOptionPane.showMessageDialog(null, String.valueOf(impresion));
            JOptionPane.showMessageDialog(null, "Intetos fallidos:"+'\n'+contOportunidades);
            if(palabraOculta.equals(String.valueOf(impresion))){
               JOptionPane.showMessageDialog(null, "GANASTE!");
               System.exit(0);
            }
        }
        if(contOportunidades>=8){ 
        JOptionPane.showMessageDialog(null, "Perdiste!!!");
        }
    }
}
