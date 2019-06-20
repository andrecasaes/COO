import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
//chcp 6500

public class EP2 {
	public static void main(String[] args) {

	}
}

class Sala {
	private String nome;
	private String local;
	private String observacoes;
	private int capacidade;

	// Sala tem uma Collection de reservas porque uma sala pode ter mais de uma reserva em diferentes horarios
	private Collection<Reserva> reservas;

	//Construtor

	Sala(String nome, int capacidade, String observacoes) {
		setNome(nome);
		setCapacidade(capacidade);
		setObservacoes(observacoes);
		setReservas(new LinkedList<Reserva>());
		addReserva(new ReservaNull(null, null, null));
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
		this.getReservas().remove(reserva);
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
		Sala sala = new Sala(nome,capacidade,descricao);
		salas.add(salas.size(),sala);
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
			for (Sala sala : salas) {
				System.out.println("Lista:");
				System.out.println(sala.getNome() + " " + sala.getCapacidade());
			}
		}
	}

	List listaDeSalas(){
		return salas;
	}

	void adicionaSala(Sala sala){
		salas.add(salas.size(), sala);
	}

	//Mudar
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

			if((inicioExistente.isBefore(inicio) && fimExistente.isAfter(fim)) 
			|| (inicioExistente.isAfter(inicio) && fimExistente.isBefore(fim))
			|| (inicioExistente.isBefore(inicio) && fimExistente.isBefore(fim))
			|| (inicioExistente.isAfter(inicio) && fimExistente.isAfter(fim))) {
				return reserva;
			}
		}

		reserva = new ReservaReal(salaAlvo, inicio, fim);
		salaAlvo.addReserva(reserva);

		return reserva;

	}

	void cancelaReserva(Reserva reserva) {
		try {
			reservaSalaChamada(reserva.sala().getNome(), reserva.inicio(), reserva.fim());
		} catch (Exception e) {
			reserva.sala().removeReserva(reserva);
			System.out.println("Reserva cancelada!");
		}
	}

	Collection reservasParaSala(Sala sala) {
		return sala.getReservas();
	}

	void imprimeReservasDaSala(Sala sala){
		System.out.println("Reservas da sala " + sala.getNome() + ":");
		for (Reserva reserva : sala.getReservas()) {
			if (reserva.sala.getNome().equals(sala.getNome())) {
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

