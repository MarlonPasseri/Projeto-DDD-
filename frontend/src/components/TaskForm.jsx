import { useEffect, useMemo, useState } from "react";

const EMPTY_FORM = {
  title: "",
  description: "",
  status: "TODO",
  dueDate: ""
};

function toFormState(task) {
  if (!task) {
    return EMPTY_FORM;
  }

  return {
    title: task.title || "",
    description: task.description || "",
    status: task.status || "TODO",
    dueDate: task.dueDate || ""
  };
}

export default function TaskForm({ initialTask, onSubmit, onCancel, loading }) {
  const [form, setForm] = useState(EMPTY_FORM);
  const isEditing = useMemo(() => Boolean(initialTask?.id), [initialTask]);

  useEffect(() => {
    setForm(toFormState(initialTask));
  }, [initialTask]);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((previous) => ({ ...previous, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    await onSubmit({
      title: form.title.trim(),
      description: form.description.trim(),
      status: form.status,
      dueDate: form.dueDate
    });

    if (!isEditing) {
      setForm(EMPTY_FORM);
    }
  }

  return (
    <form className="card form" onSubmit={handleSubmit}>
      <h2>{isEditing ? "Editar tarefa" : "Nova tarefa"}</h2>

      <label htmlFor="title">Titulo</label>
      <input
        id="title"
        name="title"
        value={form.title}
        onChange={handleChange}
        maxLength={120}
        required
      />

      <label htmlFor="description">Descricao</label>
      <textarea
        id="description"
        name="description"
        value={form.description}
        onChange={handleChange}
        maxLength={500}
        rows={4}
      />

      <div className="grid">
        <div>
          <label htmlFor="status">Status</label>
          <select id="status" name="status" value={form.status} onChange={handleChange}>
            <option value="TODO">TODO</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="DONE">DONE</option>
          </select>
        </div>

        <div>
          <label htmlFor="dueDate">Prazo</label>
          <input
            id="dueDate"
            name="dueDate"
            type="date"
            value={form.dueDate}
            onChange={handleChange}
            required
          />
        </div>
      </div>

      <div className="actions">
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? "Salvando..." : isEditing ? "Atualizar" : "Criar"}
        </button>
        {isEditing && (
          <button type="button" className="btn btn-muted" onClick={onCancel} disabled={loading}>
            Cancelar
          </button>
        )}
      </div>
    </form>
  );
}

