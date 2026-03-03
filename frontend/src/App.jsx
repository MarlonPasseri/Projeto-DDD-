import { useCallback, useEffect, useMemo, useState } from "react";
import { createTask, deleteTask, fetchTasks, updateTask } from "./api/taskApi";
import TaskForm from "./components/TaskForm";
import TaskList from "./components/TaskList";

function extractError(error) {
  return (
    error?.response?.data?.message ||
    error?.response?.data?.error ||
    error?.message ||
    "Falha ao processar a requisicao."
  );
}

export default function App() {
  const [tasks, setTasks] = useState([]);
  const [loadingList, setLoadingList] = useState(true);
  const [savingTask, setSavingTask] = useState(false);
  const [editingTask, setEditingTask] = useState(null);
  const [error, setError] = useState("");

  const loadTasks = useCallback(async () => {
    setLoadingList(true);
    setError("");

    try {
      const data = await fetchTasks();
      setTasks(data);
    } catch (requestError) {
      setError(extractError(requestError));
    } finally {
      setLoadingList(false);
    }
  }, []);

  useEffect(() => {
    loadTasks();
  }, [loadTasks]);

  async function handleSubmit(formData) {
    setSavingTask(true);
    setError("");

    try {
      if (editingTask) {
        await updateTask(editingTask.id, formData);
      } else {
        await createTask(formData);
      }

      setEditingTask(null);
      await loadTasks();
    } catch (requestError) {
      setError(extractError(requestError));
    } finally {
      setSavingTask(false);
    }
  }

  async function handleDelete(taskId) {
    if (!window.confirm("Deseja realmente excluir esta tarefa?")) {
      return;
    }

    setError("");
    try {
      await deleteTask(taskId);
      await loadTasks();
    } catch (requestError) {
      setError(extractError(requestError));
    }
  }

  const stats = useMemo(() => {
    const summary = { total: tasks.length, todo: 0, progress: 0, done: 0 };
    tasks.forEach((task) => {
      if (task.status === "DONE") summary.done += 1;
      else if (task.status === "IN_PROGRESS") summary.progress += 1;
      else summary.todo += 1;
    });
    return summary;
  }, [tasks]);

  return (
    <main className="layout">
      <header className="hero card">
        <h1>Gestao de Tarefas - TP1</h1>
        <p>Aplicacao monolitica com Spring Boot e React.</p>
        <div className="stats">
          <span>Total: {stats.total}</span>
          <span>TODO: {stats.todo}</span>
          <span>Em andamento: {stats.progress}</span>
          <span>Concluidas: {stats.done}</span>
        </div>
      </header>

      {error && <div className="error">{error}</div>}

      <section className="content">
        <TaskForm
          initialTask={editingTask}
          onSubmit={handleSubmit}
          onCancel={() => setEditingTask(null)}
          loading={savingTask}
        />
        <TaskList tasks={tasks} onEdit={setEditingTask} onDelete={handleDelete} loading={loadingList} />
      </section>
    </main>
  );
}

