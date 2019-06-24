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
			Sala sala = new SalaReal("300", 3, "Sala");
			Sala sala2 = new SalaReal("213", 3, "Sala");
			ge.adicionaSala(sala);
			ge.adicionaSala(sala2);
			ge.removeSalaChamada("215");
			System.out.println("Tentando remover uma sala que não existe:");
			ge.removeSalaChamada("1");
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
			sala = new SalaReal("100", 20, "sla");
			ge.imprimeReservasDaSala(sala);
	}
}

abstract class Sala {
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
	}

	// METODOS DE ACESSO

	//Getters

	public String getNome() throws SalaInexistenteException {
		return this.nome;
	}

	public String getLocal() throws SalaInexistenteException {
		return this.local;
	}

	public String getObservacoes() throws SalaInexistenteException {
		return this.observacoes;
	}

	public int getCapacidade() throws SalaInexistenteException {
		return this.capacidade;
	}

	public Collection<Reserva> getReservas() throws SalaInexistenteException {
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

	public void addReserva(Reserva reserva) throws ReservaInvalidaException {
		try {
			this.getReservas().add(reserva);
		} catch(SalaInexistenteException e) {
			throw new ReservaInvalidaException("A sala não existe.");
		}
	}

	public void removeReserva(Reserva reserva) throws ReservaInvalidaException {
		try {
			for(Reserva r : getReservas()) {
				if(r.inicio().isEqual(reserva.inicio()) && r.fim().isEqual(reserva.fim()) && r.sala().equals(reserva.sala())) {
					this.getReservas().remove(r);
				}
			}
		} catch(SalaInexistenteException e) {
			throw new ReservaInvalidaException("A sala não existe.");
		}
	}
}

class SalaReal extends Sala {
	SalaReal(String nome, int capacidade, String observacoes) {
		super(nome, capacidade, observacoes);
	}
}

class SalaNull extends Sala {
	SalaNull(String nome, int capacidade, String observacoes) {
		super(nome, capacidade, observacoes);
	}

	@Override
	public String getNome() throws SalaInexistenteException {
		throw new SalaInexistenteException();
	}

	@Override
	public String getLocal() throws SalaInexistenteException {
		throw new SalaInexistenteException();
	}

	@Override
	public String getObservacoes() throws SalaInexistenteException {
		throw new SalaInexistenteException();
	}

	@Override
	public int getCapacidade() throws SalaInexistenteException {
		throw new SalaInexistenteException();
	}

	@Override
	public Collection<Reserva> getReservas() throws SalaInexistenteException {
		throw new SalaInexistenteException();
	}
}

class Reserva {
	private Sala sala;
	private LocalDateTime inicio;
	private LocalDateTime fim;

	Reserva(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
		this.sala = sala;
		this.inicio = inicio;
		this.fim = fim;
	}

	public Sala sala(){
		return this.sala;
	}

	public LocalDateTime inicio(){
		return this.inicio;
	}

	public LocalDateTime fim(){
		return this.fim;
	}
}

class GerenciadorDeSalas{
	private List<Sala> salas = new LinkedList<Sala>();

	GerenciadorDeSalas() {
		salas.add(new SalaNull(null, 0, null));
	}

	void adicionaSalaChamada(String nome, int capacidade, String descricao){
		try {
			for (Sala sala : salas) {
				if (sala.getNome().equals(nome)) {
					System.out.println("A sala "+nome+" já existe");
					return;
				}
			}
		} catch(SalaInexistenteException e) {
			salas.remove(salas.iterator().next());
		}
		Sala sala = new SalaReal(nome,capacidade,descricao);
		adicionaSala(sala);
	}

	void removeSalaChamada(String nome){
		try {
			for (Sala sala : salas) {
				if (sala.getNome().equals(nome)){
					salas.remove(sala);
					return;
				} 
			}
		} catch(SalaInexistenteException e) {}
		System.out.println("A sala não pode ser removida porque não existe.");
	}

	void imprimeSalas(){
		try {
			System.out.println("Lista:");
			for (Sala sala : salas) {
				System.out.println("A sala "+ sala.getNome() + " tem capacidade de " + sala.getCapacidade()+" pessoas.");
			}
		} catch(SalaInexistenteException e) {
			System.out.println("Ainda não há salas.");
		}
	}

	List listaDeSalas(){
		return salas;
	}

	void adicionaSala(Sala sala){
		this.salas.add(sala);
	}

	Reserva reservaSalaChamada(String nome, LocalDateTime inicio, LocalDateTime fim) throws ReservaInvalidaException {
		//Sala que estamos procurando começa como uma SalaNull
		Sala salaAlvo = new SalaNull(null, 0, null);

		//procurando a sala na lista de salas
		try {
			for(Sala sala : salas) {
				if(sala.getNome().equals(nome)) {
					salaAlvo = sala;
					break;
				}
			}

			for(Reserva reservaDaSala : salaAlvo.getReservas()) {
				LocalDateTime inicioExistente = reservaDaSala.inicio();
				LocalDateTime fimExistente = reservaDaSala.fim();

				if(inicioExistente == null) break;

				if(inicioExistente.isEqual(inicio) && fimExistente.isEqual(fim)) return (new Reserva(salaAlvo, inicio, fim));

				if((inicioExistente.isBefore(inicio) && fimExistente.isAfter(fim)) 
				|| (inicioExistente.isAfter(inicio) && fimExistente.isBefore(fim))
				|| (inicioExistente.isBefore(inicio) && inicioExistente.isBefore(fim) && fimExistente.isBefore(fim) && fimExistente.isAfter(inicio))
				|| (inicioExistente.isAfter(inicio) && inicioExistente.isBefore(fim) && fimExistente.isAfter(fim) && fimExistente.isAfter(inicio))
				|| inicioExistente.isEqual(inicio) || fimExistente.isEqual(fim)) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					throw new ReservaInvalidaException("Reserva Invalida! ("+nome+": "+inicio.format(formatter)+" -> "+fim.format(formatter)+")");
				}
			}
		} catch(SalaInexistenteException e) {
			throw new ReservaInvalidaException("A sala não existe.");
		}

		Reserva reserva = new Reserva(salaAlvo, inicio, fim);
		salaAlvo.addReserva(reserva);

		return reserva;

	}

	void cancelaReserva(Reserva reserva) {
		try {
			reserva.sala().removeReserva(reserva);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			System.out.println("Reserva cancelada! (" + reserva.sala().getNome() + ": " + reserva.inicio().format(formatter) + " -> " + reserva.fim().format(formatter) + ")");
		} catch(SalaInexistenteException e) {
			System.out.println("A sala não existe.");
		} catch(ReservaInvalidaException ex) {
			System.out.println("A reserva não existe.");
		}
	}

	Collection reservasParaSala(Sala sala) {
		try {
			return sala.getReservas();
		} catch(SalaInexistenteException e) {
			System.out.println("A sala não existe.");
			return null;
		}
	}

	void imprimeReservasDaSala(Sala sala){
		try {
			boolean imprimiu = false;
			System.out.println("Reservas da sala " + sala.getNome() + ":");
			for (Reserva reserva : sala.getReservas()) {
				if (reserva.sala.getNome().equals(sala.getNome())) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					System.out.println("A sala está reservada de " + reserva.inicio.format(formatter) + " até " + reserva.fim.format(formatter));
					imprimiu = true;
				}
			}
			if(!imprimiu) {
				System.out.println("A sala ainda não possui reservas.");
			}
		} catch(SalaInexistenteException e) {
			System.out.println("A sala não existe.");
		}
	}
}//Classe

class ReservaInvalidaException extends Exception{
	ReservaInvalidaException(String mensagem){
		super(mensagem);
		System.out.println(mensagem);
	}
}

class SalaInexistenteException extends Exception{
	SalaInexistenteException(){
	}
}
