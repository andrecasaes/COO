import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class EP2{
	public static void main(String[] args) {
	
		GerenciadorDeSalas ge = new GerenciadorDeSalas();
			ge.adicionaSalaChamada("210", 60, "Sala de aula");
			ge.adicionaSalaChamada("214", 60, "Sala de aula");
			//Sala duplicada!
			ge.adicionaSalaChamada("214", 60, "Sala de aula");
			ge.adicionaSalaChamada("215", 60, "Sala de aula");
			ge.adicionaSalaChamada("216", 60, "Sala de aula");
			ge.adicionaSalaChamada("217", 60, "Sala de aula");
			ge.adicionaSalaChamada("218", 60, "Sala de aula");
			System.out.println("------- Salas adicionadas pelo metodo adicionaSalaChamada -------");
			ge.imprimeSalas();
			System.out.println("-----------------------------------------------------------------");
			Sala sala = new Sala("300", 3, "Sala");
			Sala sala2 = new Sala("213", 3, "Sala");
			ge.adicionaSala(sala);
			ge.adicionaSala(sala2);
			ge.removeSalaChamada("215");
			System.out.println("------- Adicionando as salas 213 e 300 e removendo a sala 215: -------");
			ge.imprimeSalas();
			System.out.println("----------------------------------------------------------------------");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String teste1 = "20/06/2019 18:00";
			String teste2 = "20/06/2019 19:00";
			try {
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {

			}
			try {
				teste1 = "21/06/2019 15:00";
				teste2 = "21/06/2019 17:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			try {
				teste1 = "21/06/2019 16:00";
				teste2 = "21/06/2019 18:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			try {
				teste1 = "21/06/2019 12:00";
				teste2 = "21/06/2019 16:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			try {
				teste1 = "21/06/2019 14:00";
				teste2 = "21/06/2019 18:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			try {
				teste1 = "21/06/2019 15:30";
				teste2 = "21/06/2019 16:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			try {
				teste1 = "21/06/2019 18:00";
				teste2 = "21/06/2019 19:00";
				ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
			} catch (Exception e) {
				//TODO: handle exception
			}
			ge.imprimeReservasDaSala(sala2);
			try {
				Reserva re = ge.reservaSalaChamada("213", LocalDateTime.parse(teste1, formatter), LocalDateTime.parse(teste2, formatter));
				ge.cancelaReserva(re);
				
			} catch (Exception e) {
				//TODO: handle exception
			}
			ge.imprimeReservasDaSala(sala2);

			ge.imprimeReservasDaSala(sala);
			
	}
}

class Sala {
	private String nome;
	private String local;
	private String observacoes;
	private int capacidade;

	// Sala tem uma Collection de reservas porque uma sala pode ter mais de uma reserva em diferentes horarios
	private Collection<Reserva> reservas = new LinkedList<Reserva>();

	//Construtor

	Sala(String nome, int capacidade, String observacoes) {
		setNome(nome);
		setCapacidade(capacidade);
		setObservacoes(observacoes);
		this.reservas.add(new ReservaNull(null, null, null));
	}

	// METODOS DE ACESSO

	//Getters

	public String getNome() {
		return this.nome;
	}

	public String getLocal() {
		return this.local;
	}

	public String getObservacoes() {
		return this.observacoes;
	}

	public int getCapacidade() {
		return this.capacidade;
	}

	public Collection<Reserva> getReservas() {
		return this.reservas;
	}

	//Setters

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}


	// METODOS DE SALA

	public void addReserva(Reserva reserva) {
		if(this.getReservas().iterator().next().inicio() == null) {
			this.getReservas().remove(this.getReservas().iterator().next());
		}
		this.getReservas().add(reserva);
	}

	public void removeReserva(Reserva reserva) {
		for(Reserva r : getReservas()) {
			if(r.inicio().isEqual(reserva.inicio()) && r.fim().isEqual(reserva.fim()) && r.sala().equals(reserva.sala())) {
				this.getReservas().remove(r);
			}
		}
		if(this.getReservas() == null) {
			this.getReservas().add(new ReservaNull(null, null, null));
		}
	}
}

abstract class Reserva {
	Sala sala;
	LocalDateTime inicio;
	LocalDateTime fim;

	Reserva(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
		this.sala = sala;
		this.inicio = inicio;
		this.fim = fim;
	}

	public abstract Sala sala();
	public abstract LocalDateTime fim();
	public abstract LocalDateTime inicio();
}

class ReservaReal extends Reserva {
	ReservaReal(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
		super(sala, inicio, fim);
	}

	@Override
	public Sala sala(){
		return this.sala;
	}

	@Override
	public LocalDateTime inicio(){
		return this.inicio;
	}

	@Override
	public LocalDateTime fim(){
		return this.fim;
	}
}

class ReservaNull extends Reserva {
	ReservaNull(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
		super(sala, inicio, fim);
	}

	@Override
	public Sala sala() {
		return null;
	}

	@Override
	public LocalDateTime inicio() {
		return null;
	}

	@Override
	public LocalDateTime fim() {
		return null;
	}
}

class GerenciadorDeSalas{
	List<Sala> salas = new LinkedList<>();

	void adicionaSalaChamada(String nome, int capacidade, String descricao){
		for (Sala sala : salas) {
			if (sala.getNome().equals(nome)) {
				System.out.println("A sala "+nome+" já existe");
				return;
			}
		}
		Sala sala = new Sala(nome,capacidade,descricao);
		this.salas.add(sala);
	}

	void removeSalaChamada(String nome){
		for (Sala sala : salas) {
			if (sala.getNome().equals(nome)){
				salas.remove(sala);
				break;
			} 
		}
	}

	void imprimeSalas(){
		if (!(salas == null)) {
			System.out.println("Lista:");
			for (Sala sala : salas) {
				System.out.println("A sala "+ sala.getNome() + " tem capacidade de " + sala.getCapacidade()+" pessoas.");
			}
		}
	}

	List listaDeSalas(){
		return salas;
	}

	void adicionaSala(Sala sala){
		salas.add(salas.size(), sala);
	}

	Reserva reservaSalaChamada(String nome, LocalDateTime inicio, LocalDateTime fim) throws ReservaInvalidaException {
		//Sala que estamos procurando começa como null
		Sala salaAlvo = null;

		//procurando a sala na lista de salas
		for(Sala sala : salas) {
			if(sala.getNome().equals(nome)) {
				salaAlvo = sala;
				break;
			}
		}

		//Criando uma reserva null
		Reserva reserva = new ReservaNull(null, null, null);

		//Se a sala não existir, retornamos a reserva null
		if(salaAlvo == null) throw new ReservaInvalidaException("A sala " + nome + " não existe.");

		for(Reserva reservaDaSala : salaAlvo.getReservas()) {
			LocalDateTime inicioExistente = reservaDaSala.inicio();
			LocalDateTime fimExistente = reservaDaSala.fim();

			if(inicioExistente == null) break;

			if(inicioExistente.isEqual(inicio) && fimExistente.isEqual(fim)) return (new ReservaReal(salaAlvo, inicio, fim));

			if((inicioExistente.isBefore(inicio) && fimExistente.isAfter(fim)) 
			|| (inicioExistente.isAfter(inicio) && fimExistente.isBefore(fim))
			|| (inicioExistente.isBefore(inicio) && inicioExistente.isBefore(fim) && fimExistente.isBefore(fim) && fimExistente.isAfter(inicio))
			|| (inicioExistente.isAfter(inicio) && inicioExistente.isBefore(fim) && fimExistente.isAfter(fim) && fimExistente.isAfter(inicio))
			|| inicioExistente.isEqual(inicio) || fimExistente.isEqual(fim)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				throw new ReservaInvalidaException("Reserva Invalida! ("+nome+": "+inicio.format(formatter)+" -> "+fim.format(formatter)+")");
			}
		}
		reserva = new ReservaReal(salaAlvo, inicio, fim);
		salaAlvo.addReserva(reserva);

		return reserva;

	}

	void cancelaReserva(Reserva reserva) {
			reserva.sala().removeReserva(reserva);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			System.out.println("Reserva cancelada! (" + reserva.sala().getNome() + ": " + reserva.inicio().format(formatter) + " -> " + reserva.fim().format(formatter) + ")");
	}

	Collection reservasParaSala(Sala sala) {
		return sala.getReservas();
	}

	void imprimeReservasDaSala(Sala sala){
		if (sala.getReservas().iterator().next().inicio() == null) {
			System.out.println("A sala "+sala.getNome()+" não possui reservas.");
		}else{
			System.out.println("Reservas da sala " + sala.getNome() + ":");
			for (Reserva reserva : sala.getReservas()) {
				if (reserva.sala.getNome().equals(sala.getNome())) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					System.out.println("A sala está reservada de " + reserva.inicio.format(formatter) + " até " + reserva.fim.format(formatter));
				}
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