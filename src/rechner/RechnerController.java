package rechner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RechnerController {
	static enum Type {
		FIFO, LIFO, HIFO, LOFO
	};

	@FXML
	private TextArea taInput;

	@FXML
	private TextArea taFifo;

	@FXML
	private TextArea taLifo;

	@FXML
	private TextArea taHifo;

	@FXML
	private TextArea taLofo;

	@FXML
	public void initialize() {
		taInput.textProperty().addListener((obs, old, text) -> {
			doCalculation(text);
		});
		taInput.setText("#+count,value\n#-count\n+10,10\n-5\n+5,5\n-5");
	}

	private void doCalculation(String text) {
		String[] lines = text.split("\\n");
		doCalculationFor(lines, taFifo, Type.FIFO);
		doCalculationFor(lines, taLifo, Type.LIFO);
		doCalculationFor(lines, taHifo, Type.HIFO);
		doCalculationFor(lines, taLofo, Type.LOFO);
	}

	private void doCalculationFor(String[] lines, TextArea ta, Type type) {
		List<Integer> items = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			try {
				boolean doneSth = false;
				if (line.startsWith("+")) {
					doneSth = true;
					int index = line.indexOf(',');
					if (index == -1) {
						throw new Exception("Line wrong: " + line);
					}
					int count = Integer.parseInt(line.substring(1, index));
					Integer value = Integer.valueOf(line.substring(index + 1));
					for (int i = 0; i < count; i++) {
						items.add(value);
					}
				} else if (line.startsWith("-")) {
					doneSth = true;
					int count = Integer.parseInt(line.substring(1));
					for (int i = 0; i < count; i++) {
						remove(items, type);
					}
				}

				if (doneSth) {
					sb.append("----after line: ");
					sb.append(line);
					sb.append('\n');
					Map<Integer, Integer> map = new HashMap<>();
					for (Integer value : items) {
						if (map.containsKey(value)) {
							map.put(value, map.get(value) + 1);
						} else {
							map.put(value, 1);
						}
					}

					long zaehler = 0;
					long nenner = 0;
					for (Entry<Integer, Integer> entry : map.entrySet()) {
						int count = entry.getValue();
						int value = entry.getKey();
						zaehler += count * value;
						nenner += count;
						sb.append("  ");
						sb.append(count);
						sb.append(" times ");
						sb.append(value);
						sb.append(" €\n");
					}
					sb.append("  Average price is: ");
					if (nenner != 0) {
						sb.append((zaehler / nenner));
					}
					sb.append('\n');

				}
			} catch (Exception e) {
				ta.setText("Line wrong: " + line + " Exception: " + e.getMessage());
				return;
			}
		}
		ta.setText(sb.toString());
	}

	private void remove(List<Integer> items, Type type) throws Exception {
		if (items.size() == 0) {
			throw new Exception("Want to remove but already empty.");
		}
		switch (type) {
			case FIFO :
				items.remove(0);
				break;
			case LIFO :
				items.remove(items.size() - 1);
				break;
			case HIFO :
				items.remove(getHighestItem(items));
				break;
			case LOFO :
				items.remove(getLowestItem(items));
				break;
		}
	}

	private Object getHighestItem(List<Integer> items) {
		Integer highest = items.get(0);
		for (int index = items.size() - 1; index >= 0; index--) {
			if (items.get(index).intValue() > highest.intValue()) {
				highest = items.get(index);
			}
		}
		return highest;
	}

	private Object getLowestItem(List<Integer> items) {
		Integer lowest = items.get(0);
		for (int index = items.size() - 1; index >= 0; index--) {
			if (items.get(index).intValue() < lowest.intValue()) {
				lowest = items.get(index);
			}
		}
		return lowest;
	}
}
