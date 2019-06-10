import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class MarcadorDeReuniao {
	static ArrayList<Participantes> participantes = new ArrayList<Participantes>();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("-----------------------------------");
		System.out.println("O Marcador de reunião foi iniciado!");
		System.out.println("-----------------------------------");
		System.out.println("Vamos lá!");
		System.out.println("Primeiro eu preciso saber entre quais dias será essa reunião");
		System.out.println("Insira o primeiro ");
		
		
		System.out.println("Primeiro, insira o seu nome!");
		Collection<String> entradaQualquer = new ArrayList<String>();
		System.out.println("Quantos participantes vão para a reunião?");
		int n = sc.nextInt();
		sc.nextLine();
		for (int i = n; i > 0; i--) {
			entradaQualquer.add(sc.nextLine());
		}
		//for (Object o : entradaQualquer) {
			//System.out.println(o);
		//}
		
		for (String nome : entradaQualquer) {
			participantes.add(new Participantes(nome));
		}
		
	}
	public void chamanoZap() {
		indicaDisponibilidadeDe("Andre",LocalDateTime.now(),LocalDateTime.now());
	}
	public void marcarReuniaoEntre(LocalDate dataInicial,LocalDate dataFinal,Collection<String> listaDeParticipantes) {
		
	}
	public void indicaDisponibilidadeDe(String participante,LocalDateTime inicio,LocalDateTime fim) {
		for (Participantes p : participantes) {
			if (p.nome.equals(participante)){
				p.horariosDispo.add(inicio);
				p.horariosDispo.add(fim);
			}
		}
	}
	public void mostraSobreposicao() {
		
	}
}
class Participantes {
	String nome;
	Collection<LocalDateTime> horariosDispo = new ArrayList<LocalDateTime>();
	
	Participantes(String nome){
		this.nome = nome;
	}
}

//-----------------------------------------Area do lixo ---------------------------------------------------------

//Lais a suprema,czar, imperadora do lixo
//uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
