const STATUS_LABELS = {
  TODO: "TODO",
  IN_PROGRESS: "EM_ANDAMENTO",
  DONE: "CONCLUIDA"
};

function getStatusClass(status) {
  if (status === "DONE") return "status status-done";
  if (status === "IN_PROGRESS") return "status status-progress";
  return "status status-todo";
}

export default function TaskList({ tasks, onEdit, onDelete, loading }) {
  if (loading) {
    return <section className="card">Carregando tarefas...</section>;
  }

  if (tasks.length === 0) {
    return <section className="card">Nenhuma tarefa cadastrada.</section>;
  }

  return (
    <section className="task-list">
      {tasks.map((task) => (
        <article key={task.id} className="card task-item">
          <div className="task-top">
            <h3>{task.title}</h3>
            <span className={getStatusClass(task.status)}>{STATUS_LABELS[task.status]}</span>
          </div>

          <p>{task.description || "Sem descricao."}</p>
          <small>Prazo: {task.dueDate}</small>

          <div className="actions">
            <button className="btn btn-muted" onClick={() => onEdit(task)}>
              Editar
            </button>
            <button className="btn btn-danger" onClick={() => onDelete(task.id)}>
              Excluir
            </button>
          </div>
        </article>
      ))}
    </section>
  );
}

