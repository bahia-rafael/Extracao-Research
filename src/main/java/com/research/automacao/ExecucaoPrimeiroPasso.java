/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.automacao;

import com.research.paralelismo.ConsumidorExtracaoResearchPrimeiroPasso;
import com.research.validacao.ValidacaoExtracaoResearchGate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bahia-rafael
 */
public class ExecucaoPrimeiroPasso {

    public static void main(String[] args) throws InterruptedException, IOException {

        Set<String> nomeProfessor = ValidacaoExtracaoResearchGate.retirarRepeticao("NomeLista - Primeiro Passo.txt");

        ConcurrentLinkedDeque<String> professores = new ConcurrentLinkedDeque<>(nomeProfessor);
        ConcurrentLinkedDeque<String> result = new ConcurrentLinkedDeque<>();

        ConsumidorExtracaoResearchPrimeiroPasso thread1 = new ConsumidorExtracaoResearchPrimeiroPasso(1, professores, result);
        ConsumidorExtracaoResearchPrimeiroPasso thread2 = new ConsumidorExtracaoResearchPrimeiroPasso(2, professores, result);

        thread1.start();
        thread2.start();

        while (thread1.isAlive() /*|| thread3.isAlive() || thread5.isAlive()*/) {
            try {
                System.out.println("Faltam :" + professores.size());
                Thread.sleep(10000);

            } catch (InterruptedException ex) {
                Logger.getLogger(ConsumidorExtracaoResearchPrimeiroPasso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

        FileWriter arq;
        try {
            arq = new FileWriter("Resultado - Primeiro Passo.txt", true);
            try (BufferedWriter escritor = new BufferedWriter(arq)) {
                for (String professor : result) {

                    escritor.write(professor);
                    escritor.newLine();

                }
            }
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(ValidacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
