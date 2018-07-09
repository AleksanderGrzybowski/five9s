import React from 'react';
import { Table } from 'react-bootstrap';
import ServiceInfoRow from './ServiceInfoRow';

export default function ServiceTable({services}) {
  const rows = services.map(service =>
    <ServiceInfoRow key={service.name} service={service}/>
  );

  return (
    <Table striped bordered hover style={{marginTop: 20}}>
      <thead>
      <tr>
        <th>Service</th>
        <th>Status</th>
        <th>Last output</th>
      </tr>
      </thead>
      <tbody>
      {rows}
      </tbody>
    </Table>
  )
}
