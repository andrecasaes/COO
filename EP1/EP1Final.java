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
		ArrayList<ArrayList<LocalDateTime>> resp = encontraIntervalo(0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		for(int i = 0; i < resp.get(0).size(); i++) {
			if(resp.get(0).get(i).isBefore(resp.get(1).get(i))) {
				System.out.println(resp.get(0).get(i).format(formatter) + " até " + resp.get(1).get(i).format(formatter));
			}
		}
	}

	public List<LocalDateTime> encontraInicio(int indiceParticipante) {
		List<LocalDateTime> resposta = new ArrayList<LocalDateTime>();
		if(indiceParticipante == participantes.size()-1) {
			List<LocalDateTime> aux = participantes.get(indiceParticipante).horariosDispo;
			for(int i = 0; i < aux.size(); i+=2) {
				resposta.add(aux.get(i));
			}
			return resposta;
		}
		List<LocalDateTime> restoDaLista = encontraInicio(indiceParticipante+1);
		for(int i = 0; i < participantes.get(indiceParticipante).horariosDispo.size(); i+=2) {
			for(int j = 0; j < restoDaLista.size(); j++) {
				if(participantes.get(indiceParticipante).horariosDispo.get(i).isAfter(restoDaLista.get(j))) {
					resposta.add(participantes.get(indiceParticipante).horariosDispo.get(i));
				} else {
					resposta.add(restoDaLista.get(j));
				}
			}
		}
		return resposta;
	}

	public ArrayList<ArrayList<LocalDateTime>> encontraIntervalo(int indiceParticipante) {
		ArrayList<LocalDateTime> respostaInicio = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> respostaFim = new ArrayList<LocalDateTime>();
		if(indiceParticipante == participantes.size()-1) {
			ArrayList<LocalDateTime> aux = participantes.get(indiceParticipante).horariosDispo;
			for(int i = 0; i < aux.size(); i+=2) {
				respostaInicio.add(aux.get(i));
				respostaFim.add(aux.get(i+1));
			}
			ArrayList<ArrayList<LocalDateTime>> resposta = new ArrayList<ArrayList<LocalDateTime>>();
			resposta.add(respostaInicio);
			resposta.add(respostaFim);
			return resposta;
		}
		ArrayList<ArrayList<LocalDateTime>> restoDaLista = encontraIntervalo(indiceParticipante+1);
		for(int i = 0; i < participantes.get(indiceParticipante).horariosDispo.size(); i+=2) {
			for(int j = 0; j < restoDaLista.get(0).size(); j++) {
				if(participantes.get(indiceParticipante).horariosDispo.get(i).isAfter(restoDaLista.get(0).get(j))) {
					respostaInicio.add(participantes.get(indiceParticipante).horariosDispo.get(i));
				} else {
					respostaInicio.add(restoDaLista.get(0).get(j));
				}
			}
		}
		for(int i = 0; i < participantes.get(indiceParticipante).horariosDispo.size(); i+=2) {
			for(int j = 0; j < restoDaLista.get(1).size(); j++) {
				if(participantes.get(indiceParticipante).horariosDispo.get(i+1).isBefore(restoDaLista.get(1).get(j))) {
					respostaFim.add(participantes.get(indiceParticipante).horariosDispo.get(i+1));
				} else {
					respostaFim.add(restoDaLista.get(1).get(j));
				}
			}
		}
		ArrayList<ArrayList<LocalDateTime>> resposta = new ArrayList<ArrayList<LocalDateTime>>();
		resposta.add(respostaInicio);
		resposta.add(respostaFim);
		return resposta;
	}
}
class Participantes {
	String email;
	ArrayList<LocalDateTime> horariosDispo = new ArrayList<LocalDateTime>();
	Participantes(String email){
		this.email = email;
	}
}

//-----------------------------------------Area do lixo ---------------------------------------------------------

//Lais a suprema,czar, imperadora do lixo
//uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
