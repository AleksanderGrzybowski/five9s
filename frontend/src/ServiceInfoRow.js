import React from 'react';

const classNames = {
  UP: 'table-success',
  WARN: 'table-warning',
  DOWN: 'table-danger'
};

export default function ServiceInfoRow({service}) {
  const className = classNames[service.status.status] || '';

  const failureCount = service.status.failureCount;
  const failureCounter = failureCount > 0 ? <span>({failureCount} fails)</span> : null;

  return (
    <tr className={className}>
      <td>{service.name}</td>
      <td>{service.description}</td>
      <td>{service.status.status} {failureCounter}</td>
    </tr>
  )
}
