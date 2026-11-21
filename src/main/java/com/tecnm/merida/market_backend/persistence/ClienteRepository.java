
package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Client;
import com.tecnm.merida.market_backend.persistence.crud.ClienteCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Cliente;
import com.tecnm.merida.market_backend.persistence.mapper.ClientMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository implements
        com.tecnm.merida.market_backend.domain.repository.ClientRepository {

    private final ClienteCrudRepository clienteCrudRepository;
    private final ClientMapper mapper;

    public ClienteRepository(ClienteCrudRepository clienteCrudRepository,
                             ClientMapper mapper) {
        this.clienteCrudRepository = clienteCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Client> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        clienteCrudRepository.findAll().forEach(clientes::add);
        return clientes.stream().map(mapper::toClient).toList();
    }

    @Override
    public Optional<Client> getClient(String clientId) {
        return clienteCrudRepository.findById(clientId)
                .map(mapper::toClient);
    }

    @Override
    public Client save(Client client) {
        Cliente cliente = mapper.toCliente(client);
        return mapper.toClient(clienteCrudRepository.save(cliente));
    }

    @Override
    public Optional<Client> update(Client client) {
        return clienteCrudRepository.findById(client.getClientId())
                .map(clienteExistente -> {
                    clienteExistente.setNombre(client.getName());
                    clienteExistente.setApellidos(client.getLastName());
                    clienteExistente.setCelular(client.getCellphone());
                    clienteExistente.setDireccion(client.getAddress());
                    clienteExistente.setCorreoElectronico(client.getEmail());

                    Cliente actualizado =
                            clienteCrudRepository.save(clienteExistente);

                    return mapper.toClient(actualizado);
                });
    }

    @Override
    public void delete(String clientId) {
        clienteCrudRepository.deleteById(clientId);
    }
}
