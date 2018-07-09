import React from 'react';

// For some reason Badge component doesn't work
const classNames = {
  UP: 'badge-success',
  WARN: 'badge-warning',
  DOWN: 'badge-danger'
};

export default function ServiceInfoRow({service}) {
  const badgeClassName = 'badge ' + classNames[service.status.status] || '';

  const failureCount = service.status.failureCount;
  const failureCounter = failureCount > 0 ? <span>({failureCount} fails)</span> : null;

  return (
    <tr>
      <td>{service.description}</td>
      <td>
        <span style={{fontSize: 14}} className={badgeClassName}> {service.status.status} {failureCounter} </span>
      </td>
      <td>
        <code>{service.lastMessage}</code>
      </td>
    </tr>
  )
}
