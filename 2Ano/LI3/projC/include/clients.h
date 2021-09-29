#ifndef CLIENTS_H
#define CLIENTS_H

#include <stdbool.h>
#include <glib.h>

/**
 * \brief Structure that contains a client catalog.
 * 
 * This structure is a balanced binary tree that matches a client with a list that tells us if that client made purchases in a given branch.
 */
typedef GTree* Clients;

Clients initClients();
bool addClient(Clients clients, char* clientID);
void destroyClients(Clients clients);

bool is_valid_client(char* client);

bool is_in_clients(Clients clients, char* clientID);

void updateClientBranchInfo(Clients clients, char* clientID, int branch);
bool clientBoughtInBranch(Clients clients, char* clientID, int branch);

#endif