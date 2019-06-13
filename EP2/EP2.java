import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
//chcp 65001
public class EP2 {
	public static void main(String[] args) {

		MarcadorDeReuniao m = new MarcadorDeReuniao();

		boasVindas();

		//Collection<String> listaDeParticipantes = lerParticipantes();
		//for (String email : listaDeParticipantes) {
		//	m.participantes.add(new Participantes(email));
		//}

		//LocalDate[] intervalo = lerIntervalo();

		//m.marcarReuniaoEntre(intervalo[0], intervalo[1], listaDeParticipantes);
		testaSalas();
	}

	public static void boasVindas() {
		System.out.println("-----------------------------------");
		System.out.println("O Marcador de reunião foi iniciado!");
		System.out.println("-----------------------------------");
		System.out.println("");
		System.out.println("AJUDA:");
		System.out.println(
				"Com o marcador de reunião, você pode descobrir qual o melhor horário para a sua reunião em três passos:");
		System.out.println("1 - Informe o e-mail de cada participante");
		System.out.println("2 - Insira o intervalo de tempo em que você gostaria que a reunião ocorresse");
		System.out.println("3 - Para cada participante, diga os horários disponíveis");
		System.out.println("");
		System.out.println("Para utiliza-lo, siga os passos indicados.");
		System.out.println("");
		System.out.println("Bem-vindo/a!");
		System.out.println("");
	}

	public static Collection<String> lerParticipantes() {
		Scanner sc = new Scanner(System.in);
		Collection<String> resposta = new ArrayList<String>();

		System.out.println("Quantos participantes vão para a reunião?");
		System.out.print("Número de participantes: ");
		int n = sc.nextInt();
		sc.nextLine();
		for (int i = 1; i <= n; i++) {
			System.out.print("E-mail do participante " + i + ": ");
			resposta.add(sc.nextLine());
		}
		return resposta;
	}

	public static LocalDate[] lerIntervalo() {
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate[] resposta = new LocalDate[2];

		System.out.println("Digite o intervalo para marcar a reunião. (Formato: dd-MM-yyyy)");
		do {
			System.out.print("Digite a data inicial: ");
			resposta[0] = LocalDate.parse(sc.next(), formatter);
			System.out.print("Digite a data final: ");
			resposta[1] = LocalDate.parse(sc.next(), formatter);
			if (resposta[0].isBefore(resposta[1]) || resposta[0].isEqual(resposta[1]))
				break;
			else
				System.out.println("Horário invalido!");

		} while (resposta[0].isAfter(resposta[1]));

		return resposta;
	}
	public static void testaSalas() {
		System.out.println("Bora testar as salas parça");
		GerenciadorDeSalas g = new GerenciadorDeSalas();
		g.imprimeSalas();
		System.out.println("aquiii");
		g.adicionaSalaChamada("215", 1, "teste");
		g.adicionaSalaChamada("216", 1, "teste");
		g.adicionaSalaChamada("217", 1, "teste");
		g.adicionaSalaChamada("218", 1, "teste");
		System.out.println("testeee");
		g.imprimeSalas();
		g.removeSalaChamada("217");
		g.imprimeSalas();

	}
}

class MarcadorDeReuniao {
	static ArrayList<Participantes> participantes = new ArrayList<Participantes>();

	public void marcarReuniaoEntre(LocalDate dataInicial, LocalDate dataFinal,
			Collection<String> listaDeParticipantes) {
		lerHorarios(dataInicial, dataFinal, listaDeParticipantes);
		System.out.println("O horário ideal para a reunião é:");
		mostraSobreposicao();
	}

	public void lerHorarios(LocalDate dataInicial, LocalDate dataFinal, Collection<String> listaDeParticipantes) {
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		for (Object p : listaDeParticipantes) {
			System.out.println("Dentre " + dataInicial.format(formatterData) + " e " + dataFinal.format(formatterData)
					+ ", quais são os horários disponíveis para o participante " + p + "?");
			System.out.println("(Formato: dd-MM-yyyy HH:mm)");

			String comando = "s";

			while (!comando.toUpperCase().equals("N")) {
				System.out.print("Inicio: ");
				try {
					String inicio = sc.nextLine();
					LocalDate inicioPartData = LocalDate.parse(inicio.split(" ")[0], formatterData);
					LocalDateTime inicioPartHora = LocalDateTime.parse(inicio, formatterHora);
					System.out.print("Fim: ");
					String fim = sc.nextLine();
					LocalDate fimPartData = LocalDate.parse(inicio.split(" ")[0], formatterData);
					LocalDateTime fimPartHora = LocalDateTime.parse(fim, formatterHora);

					if ((inicioPartData.isAfter(dataInicial) || inicioPartData.isEqual(dataInicial))
							&& (inicioPartData.isBefore(dataFinal) || inicioPartData.isEqual(dataFinal))
							&& (fimPartData.isBefore(dataFinal) || fimPartData.isEqual(dataFinal))
							&& (fimPartData.isAfter(dataInicial) || fimPartData.isEqual(dataInicial))) {
						if (inicioPartHora.isBefore(fimPartHora)) {
							indicaDisponibilidadeDe(p.toString(), inicioPartHora, fimPartHora);
							System.out.println("Mais algum horário para esse participante? (S/N)");
							comando = sc.nextLine();
						} else {
							System.out.println("Horário inválido! Horário final menor que o inicial.");
						}
					} else {
						System.out.println("Horário inválido! A data não está dentro do intervalo definido.");
						continue;
					}
				} catch (DateTimeParseException e) {
					System.out.println("Ops! Os valores digitados são inválidos.");
					System.out.println("Talvez você tenha errado o formato ou esquecido de digitar um horário.");
				}

			}
			System.out.println("-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
		}
	}

	public void indicaDisponibilidadeDe(String participante, LocalDateTime inicio, LocalDateTime fim) {
		for (Participantes p : participantes) {
			if (p.email.equals(participante)) {
				p.horariosDispo.add(inicio);
				p.horariosDispo.add(fim);
			}
		}
	}

	public void mostraSobreposicao() {
		ArrayList<ArrayList<LocalDateTime>> resp = encontraIntervalo(0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		for (int i = 0; i < resp.get(0).size(); i++) {
			if (resp.get(0).get(i).isBefore(resp.get(1).get(i))) {
				System.out
						.println(resp.get(0).get(i).format(formatter) + " até " + resp.get(1).get(i).format(formatter));
			}
		}
	}

	public ArrayList<ArrayList<LocalDateTime>> encontraIntervalo(int indiceParticipante) {
		ArrayList<LocalDateTime> respostaInicio = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> respostaFim = new ArrayList<LocalDateTime>();

		if (indiceParticipante == participantes.size() - 1) {
			ArrayList<LocalDateTime> aux = participantes.get(indiceParticipante).horariosDispo;
			for (int i = 0; i < aux.size(); i += 2) {
				respostaInicio.add(aux.get(i));
				respostaFim.add(aux.get(i + 1));
			}
			ArrayList<ArrayList<LocalDateTime>> resposta = new ArrayList<ArrayList<LocalDateTime>>();
			resposta.add(respostaInicio);
			resposta.add(respostaFim);
			return resposta;
		}

		ArrayList<ArrayList<LocalDateTime>> restoDaLista = encontraIntervalo(indiceParticipante + 1);

		for (int i = 0; i < participantes.get(indiceParticipante).horariosDispo.size(); i += 2) {
			for (int j = 0; j < restoDaLista.get(0).size(); j++) {
				if (participantes.get(indiceParticipante).horariosDispo.get(i).isAfter(restoDaLista.get(0).get(j))) {
					respostaInicio.add(participantes.get(indiceParticipante).horariosDispo.get(i));
				} else {
					respostaInicio.add(restoDaLista.get(0).get(j));
				}
			}
		}

		for (int i = 0; i < participantes.get(indiceParticipante).horariosDispo.size(); i += 2) {
			for (int j = 0; j < restoDaLista.get(1).size(); j++) {
				if (participantes.get(indiceParticipante).horariosDispo.get(i + 1)
						.isBefore(restoDaLista.get(1).get(j))) {
					respostaFim.add(participantes.get(indiceParticipante).horariosDispo.get(i + 1));
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

	Participantes(String email) {
		this.email = email;
	}
}
class Sala{
	String nome;
	String local;
	String observacoes;
	int capacidade;

	Sala(String nome,int capacidade,String observacoes){
		this.nome = nome;
		this.capacidade = capacidade;
		this.observacoes = observacoes;
	}
}
class Reserva extends ReservaAbstrata{
	Sala sala;
	LocalDateTime inicio;
	LocalDateTime fim;

	Reserva(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
		this.sala = sala;
		this.inicio = inicio;
		this.fim = fim;
	}
	public Sala getSala(){
		return this.sala;
	}
	public LocalDateTime getInicio(){
		return this.inicio;
	}
	public LocalDateTime getFim(){
		return this.fim;
	}
}
class ReservaNull extends ReservaAbstrata{
	
}
abstract class ReservaAbstrata{

}
class GerenciadorDeSalas{
	List<Sala> salas = new LinkedList<>();
	List<Reserva> reservas = new LinkedList<>();
	void adicionaSalaChamada(String nome, int capacidade, String descricao){
		Sala sala = new Sala(nome,capacidade,descricao);
		salas.add(salas.size(),sala);
	}
	void removeSalaChamada(String nome){
		for (Sala sala : salas) {
			if (sala.nome.equals(nome)){
				salas.remove(sala);
				break;
			} 
		}
	}
	void imprimeSalas(){
		if (!(salas == null)) {
			for (Sala sala : salas) {
				System.out.println("Lista:");
				System.out.println(sala.nome + " " + sala.capacidade);
			}
		}
	}
	List listaDeSalas(){
		return salas;
	}
	void adicionaSala(Sala sala){
		salas.add(salas.size(), sala);
	}
	Reserva reservaSalaChamada(String nome,LocalDateTime inicio, LocalDateTime fim) throws ReservaInvalidaException{
		Reserva novaReserva = null;
		for (Reserva reserva : reservas) {
			if (reserva.sala.nome.equals(nome) && reserva.inicio == inicio && reserva.fim == fim){
				throw new ReservaInvalidaException("Reserva já criada");
			}
		}
		for (Sala sala : salas) {
			if (sala.nome.equals(nome)) {
				novaReserva = new Reserva(sala, inicio, fim);
				reservas.add(novaReserva);
			}
		}
		if (novaReserva == null) {
			throw new ReservaInvalidaException("Sala não criada");
		}
		return novaReserva;
	}
	void cancelaReserva(Reserva reserva){
		try {
			reservaSalaChamada(reserva.sala.nome, reserva.inicio, reserva.fim);
		} catch (Exception e) {
			reservas.remove(reserva);
			System.out.println("Reserva cancelada!");
		}
	}
	void reservasParaSala(Sala sala) {
		//Restorna a uma collection???
	}
	//imprimeReservasDaSala
	void imprimeReservasDaSala(Sala sala){
		System.out.println("Reservas da sala " + sala.nome + ":");
		for (Reserva reserva : reservas) {
			if (reserva.sala.nome.equals(sala.nome)) {
				System.out.println("A sala está reservada de " + reserva.inicio + " até " + reserva.fim);
			}
		}
	}
}//Classe

class ReservaInvalidaException extends Exception{
	ReservaInvalidaException(String mensagem){
		super(mensagem);
		System.out.println(mensagem);
	}
}

