import javax.swing.*; // Perpustakaan GUI Swing
import javax.swing.border.TitledBorder; // Untuk menambahkan border dengan judul
import javax.swing.table.DefaultTableModel; // Model tabel untuk JTable
import java.awt.*; // Perpustakaan untuk pengaturan layout dan grafis
import java.io.*; // Perpustakaan untuk operasi file
import java.util.ArrayList; // ArrayList untuk menyimpan riwayat mood
import java.util.HashMap; // HashMap untuk menyimpan pasangan nama mood dan gambar
import java.util.Map; // Perpustakaan untuk struktur data Map

public class MoodTrackerApp extends JFrame { // Inherits JFrame untuk membangun GUI aplikasi
    private JButton tomboltambahMood; // Tombol untuk menambah mood
    private JButton lihatRiwayat; // Tombol untuk melihat riwayat
    private JButton simpanFile; // Tombol untuk menyimpan riwayat ke file
    private JPanel menuUtama; // Panel utama untuk menampung komponen di layar utama

    private JFrame pilihmoodFrame; // Jendela untuk memilih mood
    private JFrame riwayatFrame; // Jendela untuk melihat riwayat mood
    private JTable tabelRiwayat; // Tabel untuk menampilkan riwayat
    private DefaultTableModel tableModel; // Model data untuk tabel

    private JTextArea notesArea; // Area untuk catatan mood
    private ArrayList<MoodEntry> moodHistory; // List untuk menyimpan riwayat mood
    private final String defaultFilePath = "C:\\Users\\lenovo\\IdeaProjects\\UAP\\riwayat_mood.txt"; // Lokasi file riwayat
    private final String bgUtama = "C://Users//lenovo//IdeaProjects//UAP//images//bg2.jpg"; // Gambar background untuk menu utama
    private final String bgPilihmood = "C://Users//lenovo//IdeaProjects//UAP//images//bg.jpg"; // Gambar background untuk pilihan mood
    private final String bgRiwayat = "C://Users//lenovo//IdeaProjects//UAP//images//bg.jpg"; // Gambar background untuk riwayat mood

    public MoodTrackerApp() { // Konstruktor utama
        moodHistory = new ArrayList<>(); // Inisialisasi list riwayat mood

        setTitle("Mood Tracker Harian"); // Judul aplikasi
        setSize(400, 300); // Ukuran awal jendela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Tindakan ketika aplikasi ditutup

        menuUtama = new BackgroundPanel(bgUtama); // Panel dengan latar belakang kustom
        menuUtama.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout untuk tata letak fleksibel

        tomboltambahMood = new JButton("Tambah Mood Baru"); // Membuat tombol "Tambah Mood Baru"
        lihatRiwayat = new JButton("Lihat Riwayat Mood"); // Membuat tombol "Lihat Riwayat Mood"
        simpanFile = new JButton("Simpan ke File"); // Tombol untuk menyimpan ke file

        // Pengaturan warna teks dan latar belakang tombol
        tomboltambahMood.setForeground(Color.decode("#FFFFFF"));
        tomboltambahMood.setFont(new Font("Arial", Font.BOLD, 16));
        tomboltambahMood.setBackground(new Color(128, 0, 128, 128));

        lihatRiwayat.setForeground(Color.decode("#FFFFFF"));
        lihatRiwayat.setFont(new Font("Arial", Font.BOLD, 16));
        lihatRiwayat.setBackground(new Color(128, 0, 128, 128));

        tomboltambahMood.setBorderPainted(true); // Menampilkan border tombol
        lihatRiwayat.setBorderPainted(true);

        GridBagConstraints gbc = new GridBagConstraints(); // Objek untuk pengaturan tata letak
        gbc.insets = new Insets(10, 10, 10, 10); // Jarak antar elemen
        gbc.gridx = 0; // Posisi horizontal elemen
        gbc.gridy = 0; // Posisi vertikal elemen
        menuUtama.add(tomboltambahMood, gbc); // Menambahkan tombol ke panel utama

        gbc.gridy = 1;
        menuUtama.add(lihatRiwayat, gbc); // Menambahkan tombol ke panel utama

        gbc.gridy = 2;
        menuUtama.add(simpanFile, gbc); // Menambahkan tombol ke panel utama

        add(menuUtama); // Menambahkan panel utama ke JFrame

        // Menambahkan aksi untuk tombol
        tomboltambahMood.addActionListener(e -> tampilkanPilihanmood());
        lihatRiwayat.addActionListener(e -> tampilkanRiwayat());
        simpanFile.addActionListener(e -> simpanRiwayatKeFile());

        jendelaTambahmood(); // Inisialisasi jendela tambah mood
        jendelaRiwayat(); // Inisialisasi jendela riwayat
    }

    private void jendelaTambahmood() { // Membuat jendela untuk menambah mood baru
        try {
            pilihmoodFrame = new JFrame("Tambah Mood Hari Ini"); // Jendela baru untuk memilih mood
            pilihmoodFrame.setSize(600, 500); // Mengatur ukuran jendela
            pilihmoodFrame.setContentPane(new BackgroundPanel(bgPilihmood)); // Menambahkan latar belakang kustom

            JPanel moodPanel = new JPanel(new GridLayout(3, 4, 10, 10)); // Panel dengan tata letak grid untuk mood
            moodPanel.setOpaque(false); // Membuat panel transparan
            TitledBorder border = BorderFactory.createTitledBorder("Mood Hari Ini"); // Membuat border dengan judul
            border.setTitleColor(Color.WHITE); // Mengatur warna judul border
            moodPanel.setBorder(border); // Menambahkan border ke panel

            Map<String, String> moodImages = new HashMap<>(); // Map untuk menyimpan mood dan gambar
            // Menambahkan pasangan nama mood dan gambar
            moodImages.put("Sangat Senang", "C:/Users/lenovo/IdeaProjects/UAP/images/sangat senang.png");
            moodImages.put("Senang", "C:/Users/lenovo/IdeaProjects/UAP/images/senang.png");
            moodImages.put("Biasa Saja", "C:/Users/lenovo/IdeaProjects/UAP/images/biasa.png");
            moodImages.put("Sedih", "C:/Users/lenovo/IdeaProjects/UAP/images/sedih.png");
            moodImages.put("Sangat Sedih", "C:/Users/lenovo/IdeaProjects/UAP/images/sangat sedih.png");
            moodImages.put("Marah", "C:/Users/lenovo/IdeaProjects/UAP/images/marah.png");
            moodImages.put("Stress", "C:/Users/lenovo/IdeaProjects/UAP/images/stress.png");
            moodImages.put("Lelah", "C:/Users/lenovo/IdeaProjects/UAP/images/lelah.png");
            moodImages.put("Bersemanagat", "C:/Users/lenovo/IdeaProjects/UAP/images/bersemangat.png");
            moodImages.put("Tenang", "C:/Users/lenovo/IdeaProjects/UAP/images/tenang.png");

            ButtonGroup moodGroup = new ButtonGroup(); // Grup untuk tombol mood agar hanya satu yang bisa dipilih

            for (Map.Entry<String, String> entry : moodImages.entrySet()) {
                moodPanel.add(createMoodButton(entry.getKey(), entry.getValue(), moodGroup)); // Membuat tombol mood
            }

            JPanel notesPanel = new JPanel(new BorderLayout()); // Panel untuk catatan tambahan
            notesPanel.setOpaque(false); // Membuat panel transparan
            JLabel notesLabel = new JLabel("Catatan (Opsional):"); // Label untuk catatan
            notesArea = new JTextArea(3, 20); // Area teks untuk catatan
            JScrollPane notesScrollPane = new JScrollPane(notesArea); // Scroll pane untuk area teks
            notesLabel.setForeground(Color.WHITE); // Warna teks label
            notesPanel.add(notesLabel, BorderLayout.NORTH); // Menambahkan label ke panel
            notesPanel.add(notesScrollPane, BorderLayout.CENTER); // Menambahkan scroll pane ke panel

            JButton saveMoodButton = new JButton("Simpan Mood"); // Tombol untuk menyimpan mood
            saveMoodButton.setBackground(Color.WHITE); // Warna latar belakang tombol
            saveMoodButton.setForeground(Color.BLACK); // Warna teks tombol
            saveMoodButton.addActionListener(e -> { // Aksi ketika tombol ditekan
                String selectedMood = null;

                // Mencari mood yang dipilih
                for (Component component : moodPanel.getComponents()) {
                    if (component instanceof JToggleButton) { // Mengecek jika komponen adalah tombol toggle
                        JToggleButton button = (JToggleButton) component;
                        if (button.isSelected()) { // Mengecek jika tombol dipilih
                            selectedMood = button.getText(); // Mendapatkan teks mood
                            break;
                        }
                    }
                }

                if (selectedMood == null) { // Validasi jika mood belum dipilih
                    JOptionPane.showMessageDialog(pilihmoodFrame, "Pilih mood terlebih dahulu!"); // Pesan error
                    return;
                }

                String note = notesArea.getText(); // Mendapatkan teks catatan
                moodHistory.add(new MoodEntry(selectedMood, note)); // Menambahkan entry mood baru

                JOptionPane.showMessageDialog(pilihmoodFrame, "Mood berhasil disimpan!"); // Pesan sukses
                notesArea.setText(""); // Mengosongkan area teks
            });

            JButton backButton = new JButton("Back"); // Tombol kembali
            backButton.setBackground(Color.WHITE); // Warna latar belakang tombol
            backButton.setForeground(Color.BLACK); // Warna teks tombol
            backButton.addActionListener(e -> pilihmoodFrame.setVisible(false)); // Menutup jendela

            JPanel bottomPanel = new JPanel(new BorderLayout()); // Panel bawah untuk tombol
            bottomPanel.setOpaque(false); // Membuat panel transparan
            bottomPanel.add(backButton, BorderLayout.WEST); // Menambahkan tombol kembali
            bottomPanel.add(saveMoodButton, BorderLayout.CENTER); // Menambahkan tombol simpan mood

            pilihmoodFrame.setLayout(new BorderLayout()); // Tata letak jendela
            pilihmoodFrame.add(moodPanel, BorderLayout.CENTER); // Menambahkan panel mood
            pilihmoodFrame.add(notesPanel, BorderLayout.SOUTH); // Menambahkan panel catatan
            pilihmoodFrame.add(bottomPanel, BorderLayout.NORTH); // Menambahkan panel tombol
        } catch (Exception ex) { // Penanganan error
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat membuka jendela mood: " + ex.getMessage());
        }
    }

    private JToggleButton createMoodButton(String mood, String imagePath, ButtonGroup group) { // Membuat tombol mood
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Mengatur ukuran gambar
        JToggleButton button = new JToggleButton(mood, icon); // Membuat tombol toggle
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Mengatur posisi teks horizontal
        button.setVerticalTextPosition(SwingConstants.BOTTOM); // Mengatur posisi teks vertikal
        button.setBackground(new Color(128, 128, 128, 128)); // Warna latar belakang tombol
        button.setBorderPainted(false); // Tidak menampilkan border
        button.setForeground(Color.WHITE); // Warna teks
        button.setContentAreaFilled(true); // Mengatur area isi tombol
        group.add(button); // Menambahkan tombol ke grup
        return button;
    }

    private void jendelaRiwayat() { // Membuat jendela untuk melihat riwayat mood
        riwayatFrame = new JFrame("Riwayat Mood Anda"); // Jendela baru
        riwayatFrame.setSize(600, 400); // Ukuran jendela

        BackgroundPanel historyPanel = new BackgroundPanel(bgRiwayat); // Panel dengan latar belakang kustom
        historyPanel.setLayout(new BorderLayout()); // Tata letak panel

        String[] columnNames = {"Tanggal", "Mood", "Catatan"}; // Nama kolom tabel
        tableModel = new DefaultTableModel(columnNames, 0); // Model tabel
        tabelRiwayat = new JTable(tableModel); // Tabel untuk menampilkan data
        JScrollPane tableScrollPane = new JScrollPane(tabelRiwayat); // Scroll pane untuk tabel
        tableScrollPane.setOpaque(false); // Membuat scroll pane transparan
        tableScrollPane.getViewport().setOpaque(false); // Membuat viewport transparan

        JButton refreshButton = new JButton("Refresh"); // Tombol untuk memperbarui tabel
        refreshButton.setBackground(Color.WHITE); // Warna latar belakang tombol
        refreshButton.setForeground(Color.BLACK); // Warna teks tombol
        refreshButton.addActionListener(e -> refreshHistoryTable()); // Aksi tombol refresh

        JButton deleteButton = new JButton("Hapus Riwayat Terpilih"); // Tombol untuk menghapus entri terpilih
        deleteButton.setBackground(Color.WHITE); // Warna latar belakang tombol
        deleteButton.setForeground(Color.BLACK); // Warna teks tombol
        deleteButton.addActionListener(e -> deleteSelectedEntry()); // Aksi tombol hapus

        JButton backButton = new JButton("Back"); // Tombol kembali
        backButton.setBackground(Color.WHITE); // Warna latar belakang tombol
        backButton.setForeground(Color.BLACK); // Warna teks tombol
        backButton.addActionListener(e -> riwayatFrame.setVisible(false)); // Menutup jendela

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel untuk tombol
        buttonPanel.setOpaque(false); // Membuat panel transparan
        buttonPanel.add(refreshButton); // Menambahkan tombol refresh
        buttonPanel.add(deleteButton); // Menambahkan tombol hapus
        buttonPanel.add(backButton); // Menambahkan tombol kembali

        historyPanel.add(tableScrollPane, BorderLayout.CENTER); // Menambahkan tabel ke panel
        historyPanel.add(buttonPanel, BorderLayout.SOUTH); // Menambahkan tombol ke panel bawah

        riwayatFrame.setContentPane(historyPanel); // Menambahkan panel ke jendela
    }

    private void refreshHistoryTable() { // Memperbarui tabel riwayat mood
        tableModel.setRowCount(0); // Menghapus semua baris di tabel

        for (MoodEntry entry : moodHistory) { // Menambahkan semua entri dari riwayat ke tabel
            tableModel.addRow(new Object[]{entry.getDate(), entry.getMood(), entry.getNote()});
        }
    }

    private void deleteSelectedEntry() { // Menghapus entri terpilih di tabel
        int selectedRow = tabelRiwayat.getSelectedRow(); // Mendapatkan baris terpilih

        if (selectedRow == -1) { // Validasi jika tidak ada baris yang dipilih
            JOptionPane.showMessageDialog(riwayatFrame, "Pilih riwayat yang ingin dihapus!"); // Pesan error
            return;
        }

        moodHistory.remove(selectedRow); // Menghapus entri dari list riwayat
        refreshHistoryTable(); // Memperbarui tabel
        JOptionPane.showMessageDialog(riwayatFrame, "Riwayat berhasil dihapus!"); // Pesan sukses
    }

    private void tampilkanPilihanmood() { // Menampilkan jendela pilihan mood
        pilihmoodFrame.setVisible(true);
    }

    private void tampilkanRiwayat() { // Menampilkan jendela riwayat mood
        refreshHistoryTable(); // Memperbarui tabel
        riwayatFrame.setVisible(true);
    }

    private void simpanRiwayatKeFile() { // Menyimpan riwayat ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFilePath))) { // Membuka file untuk ditulis
            for (MoodEntry entry : moodHistory) { // Menulis setiap entri riwayat ke file
                writer.write(entry.getDate() + ";" + entry.getMood() + ";" + entry.getNote());
                writer.newLine(); // Menambahkan baris baru
            }
            JOptionPane.showMessageDialog(this, "Riwayat berhasil disimpan ke file!"); // Pesan sukses
        } catch (IOException e) { // Penanganan error jika terjadi masalah file
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }

    public static void main(String[] args) { // Metode utama untuk menjalankan aplikasi
        SwingUtilities.invokeLater(() -> new MoodTrackerApp().setVisible(true)); // Menjalankan aplikasi pada event dispatch thread
    }
}

// Kelas untuk menyimpan data mood
class MoodEntry {
    private String mood; // Mood
    private String note; // Catatan
    private String date; // Tanggal

    public MoodEntry(String mood, String note, String date) { // Konstruktor lengkap
        this.mood = mood;
        this.note = note;
        this.date = date;
    }

    public MoodEntry(String mood, String note) { // Konstruktor dengan tanggal otomatis
        this(mood, note, java.time.LocalDate.now().toString());
    }

    public String getMood() {
        return mood;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }
}

// Panel kustom dengan latar belakang gambar
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) { // Konstruktor dengan jalur gambar
        try {
            backgroundImage = new ImageIcon(filePath).getImage();
        } catch (Exception e) {
            System.err.println("Gagal memuat gambar latar belakang: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) { // Menggambar gambar latar belakang
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
