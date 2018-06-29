import React from 'react';

const classNames = {
  UP: 'table-success',
  WARN: 'table-warning',
  DOWN: 'table-danger'
};

export default function ServiceInfoRow({service}) {
  const className = classNames[service.status] || '';
 
  return (
    <tr className={className}>
      <td>{service.name}</td>
      <td>{service.description}</td>
      <td>{service.status}</td>
    </tr>
  )
}
