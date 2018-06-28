import React from 'react';

export default function ServiceInfoRow({service}) {
  return (
    <li>{service.name} - {service.description} - {service.status}</li>
  )
}
