/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.automacao;

import com.research.model.ProfessorSearchFirst;
import com.research.paralelismo.ConsumidorExtracaoResearchPrimeiroPasso;
import com.research.util.FileUtil;
import com.research.validacao.ValidacaoExtracaoResearchGate;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

/**
 *
 * @author bahia-rafael
 */
public class ExecucaoPrimeiroPasso {

    public static void main(String[] args) throws InterruptedException, IOException {

        Set<String> nomeProfessor = ValidacaoExtracaoResearchGate.retirarRepeticao("NomeLista - Primeiro Passo - Segunda Parte.txt");

        ConcurrentLinkedDeque<String> professores = new ConcurrentLinkedDeque<>(nomeProfessor);
        ConcurrentLinkedDeque<ProfessorSearchFirst> result = new ConcurrentLinkedDeque<>();

        ConsumidorExtracaoResearchPrimeiroPasso thread1 = new ConsumidorExtracaoResearchPrimeiroPasso(1, professores, result);
        ConsumidorExtracaoResearchPrimeiroPasso thread2 = new ConsumidorExtracaoResearchPrimeiroPasso(2, professores, result);

        thread1.start();
        thread2.start();

        while (thread2.isAlive() || thread1.isAlive()) {
            try {
                System.out.println("Faltam :" + professores.size());
                Thread.sleep(10000);

            } catch (InterruptedException ex) {
                Logger.getLogger(ConsumidorExtracaoResearchPrimeiroPasso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

        FileUtil.writerFile("Resultado - Primeiro Passo - Segunda Parte.txt", result);

    }

}
