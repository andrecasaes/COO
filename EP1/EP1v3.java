// chcp 65001
// cd C:\Users\Andre\Desktop\3. Semestre\COO
// javac EP1.java
// java EP1
// sa
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class EP1 {
	public static void main(String[] args) {
		MarcadorDeReuniao m = new MarcadorDeReuniao();
		Scanner sc = new Scanner(System.in);
		System.out.println("-----------------------------------");
		System.out.println("O Marcador de reunião foi iniciado!");
		System.out.println("-----------------------------------");
		System.out.println("Vamos lá!");
		System.out.println("Primeiro eu preciso saber entre quais dias será essa reunião");
		System.out.println("Insira o primeiro ");
		
		
		System.out.println("Primeiro, insira o seu email!");
		Collection<String> listaDeParticipantes = new ArrayList<String>();
		System.out.println("Quantos participantes vão para a reunião?");
		System.out.print("Número de participantes: ");
		int n = sc.nextInt();
		sc.nextLine();
		for (int i = 1; i <= n; i++) {
			System.out.print("E-mail do participante " + i + ": ");
			listaDeParticipantes.add(sc.nextLine());
		}
		//for (Object o : listaDeParticipantes) {
			//System.out.println(o);
		//}
		
		for (String email : listaDeParticipantes) {
			m.participantes.add(new Participantes(email));
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		System.out.println("Digite o intervalo para marcar a reunião.");
		LocalDate inicio;
		LocalDate fim;
		do {
			System.out.print("Digite a data inicial: ");
			inicio = LocalDate.parse(sc.next(),formatter);
			System.out.print("Digite a data final: ");
			fim = LocalDate.parse(sc.next(),formatter);
			if (inicio.isBefore(fim)) break;
			else System.out.println("Horário invalido!");

		} while(inicio.isAfter(fim));
		m.marcarReuniaoEntre(inicio, fim, listaDeParticipantes);
	}
}
class MarcadorDeReuniao {
	static ArrayList<Participantes> participantes = new ArrayList<Participantes>();
	public void marcarReuniaoEntre(LocalDate dataInicial, LocalDate dataFinal, Collection<String> listaDeParticipantes) {
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(Object p : listaDeParticipantes) {
			System.out.println("Dentre " + dataInicial.format(formatter2) + " e " + dataFinal.format(formatter2) + ", quais são os horários disponíveis para o participante " + p + "?");
			System.out.println("Formato: dd-MM-yyyy HH:mm");
			String comando = "s";
			while(!comando.toUpperCase().equals("N")) {
				System.out.print("Inicio: ");
				try {
					
				
				String inicio = sc.nextLine();
				LocalDate inicioPartData = LocalDate.parse(inicio.split(" ")[0], formatter2);
				LocalDateTime inicioPartDataHora = LocalDateTime.parse(inicio, formatter);
				System.out.print("Fim: ");
				String fim = sc.nextLine();
				LocalDate fimPartData = LocalDate.parse(inicio.split(" ")[0], formatter2);
				LocalDateTime fimPartDataHora = LocalDateTime.parse(fim, formatter);
				
				if((inicioPartData.isAfter(dataInicial) || inicioPartData.isEqual(dataInicial)) 
					&& (inicioPartData.isBefore(dataFinal) || inicioPartData.isEqual(dataFinal))
					&& (fimPartData.isBefore(dataFinal) || fimPartData.isEqual(dataFinal)) 
					&& (fimPartData.isAfter(dataInicial) || fimPartData.isEqual(dataInicial))) {
					if (inicioPartDataHora.isBefore(fimPartDataHora)) {
						indicaDisponibilidadeDe(p.toString(), inicioPartDataHora, fimPartDataHora);
						System.out.println("Mais algum horário para esse participante? (S/N)");
						comando = sc.nextLine();
					}else{
						System.out.println("Horário inválido! Horário final menor que o inicial.");
					}
				} else {
					System.out.println("Horário inválido! Insira um horário dentro do intervalo definido.");
					continue;
				}
				} catch (DateTimeParseException e) {
					System.out.println("Valores digitados invalidos!");
				}
			}
			System.out.println("-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
		}
			System.out.println("O horário ideal para a reunião é:");
			mostraSobreposicao();
	}
	public void indicaDisponibilidadeDe(String participante, LocalDateTime inicio, LocalDateTime fim) {
		for (Participantes p : participantes) {
			if (p.email.equals(participante)){
					p.horariosDispo.add(inicio);
					p.horariosDispo.add(fim);
			}
		}
	}
	public void mostraSobreposicao() {
		for (int i = 0; i < participantes.get(0).horariosDispo.size(); i=i+2) {
			LocalDateTime maxDoMin = participantes.get(0).horariosDispo.get(i);
			LocalDateTime minDoMax = participantes.get(0).horariosDispo.get(i+1);
			for (int j = 0; j < participantes.get(1).horariosDispo.size(); j=j+2) {
				if (maxDoMin.isBefore(participantes.get(1).horariosDispo.get(j))) maxDoMin = participantes.get(1).horariosDispo.get(j);
				if (minDoMax.isAfter(participantes.get(1).horariosDispo.get(j+1))) minDoMax = participantes.get(1).horariosDispo.get(j+1);
				for (int K = 0; K < participantes.get(2).horariosDispo.size(); K=K+2) {
					if (maxDoMin.isBefore(participantes.get(2).horariosDispo.get(K))) maxDoMin = participantes.get(2).horariosDispo.get(K);
					if (minDoMax.isAfter(participantes.get(2).horariosDispo.get(K+1))) minDoMax = participantes.get(2).horariosDispo.get(K+1);
					if (maxDoMin.isBefore(minDoMax)) {
						System.out.println(maxDoMin);
						System.out.println(minDoMax);
					}
				}
			}
		}
	}
}
class Participantes {
	String email;
	List<LocalDateTime> horariosDispo = new ArrayList<LocalDateTime>();
	Participantes(String email){
		this.email = email;
	}
}

//-----------------------------------------Area do lixo ---------------------------------------------------------

//Lais a suprema,czar, imperadora do lixo
//uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
